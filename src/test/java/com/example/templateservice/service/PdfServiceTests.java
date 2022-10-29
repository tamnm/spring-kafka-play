package com.example.templateservice.service;

import com.example.templateservice.exception.PdfGenerationException;
import com.openhtmltopdf.pdfboxout.visualtester.PdfVisualTester;
import lombok.NonNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {PdfServiceImpl.class})
public class PdfServiceTests {

    @Autowired
    private PdfService pdfService;

    @Test
    void generate_simple_pdf() throws IOException, PdfGenerationException {
        try(var os = new ByteArrayOutputStream()) {
            pdfService.htmlToPDf("<h2>hello world</h2>", os);

            var expectedFile = Path.of("src","test","resources","test-fixtures","h2.pdf");
            var diffPath  = Path.of("target","test-output","dif");

            var expectedPdfBytes = Files.readAllBytes(expectedFile);

            var identical = compare("h2", expectedPdfBytes, os.toByteArray(), diffPath);
            assertThat(identical).isTrue();
        }
    }

    @Test
    void generate_table_pdf() throws IOException, PdfGenerationException {
        try(var os = new ByteArrayOutputStream()) {

            var htmlPath = Path.of("src","test","resources","test-html","simple-table.html");
            var expectedFile = Path.of("src","test","resources","test-fixtures","simple_table.pdf");
            var diffPath  = Path.of("target","test-output","dif");

            pdfService.htmlToPDf(Files.readString(htmlPath, StandardCharsets.UTF_8), os);
//            Files.write(expectedFile, os.toByteArray(), StandardOpenOption.TRUNCATE_EXISTING);

            var expectedPdfBytes = Files.readAllBytes(expectedFile);

            var identical = compare("simple-table", expectedPdfBytes, os.toByteArray(), diffPath);
            assertThat(identical).isTrue();
        }
    }

    private static boolean compare(@NonNull String testName, @NonNull byte[] expectedPdfBytes, @NonNull byte[] actualPdfBytes, Path diffDirectory) throws IOException {
        var storeDiff = diffDirectory != null;

        if(storeDiff && !Files.exists(diffDirectory))
            Files.createDirectories(diffDirectory);

        var problems = PdfVisualTester.comparePdfDocuments(expectedPdfBytes, actualPdfBytes, testName, false);

        if (!problems.isEmpty()) {

            System.err.println("Found problems with test case (" + testName + "):");
            System.err.println(problems.stream().map(p -> p.logMessage).collect(Collectors.joining("\n    ", "[\n    ", "\n]")));

        }
        if(storeDiff) {
            for (var result : problems) {
                if (result.testImages != null) {
                    var diffPath = Path.of(diffDirectory.toString(), String.format("%s---%d---diff.png", testName, result.pageNumber));
                    ImageIO.write(result.testImages.createDiff(), "png", diffPath.toFile());
                }
            }
        }

        return problems.isEmpty();
    }

}
