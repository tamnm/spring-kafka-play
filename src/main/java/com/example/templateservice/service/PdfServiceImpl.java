package com.example.templateservice.service;

import com.example.templateservice.exception.PdfGenerationException;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.NonNull;
import org.apache.logging.log4j.util.Strings;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;

@Service
public class PdfServiceImpl implements PdfService {
    private final HtmlCleaner cleaner = new HtmlCleaner();

    @Override
    public void htmlToPDf(@NonNull String html, @NonNull OutputStream outputStream) throws PdfGenerationException {

        final PdfRendererBuilder builder = new PdfRendererBuilder();

        builder.useFastMode();

        builder.withHtmlContent(cleanHtml(html), Strings.EMPTY);
        builder.toStream(outputStream);

        try {
            builder.run();
        } catch (IOException e) {
            throw new PdfGenerationException(e.getMessage(), e);
        }
    }

    private String cleanHtml(String html){
        var rootTagNode = cleaner.clean(html);

        // set up properties for the serializer (optional, see online docs)
        var cleanerProperties = cleaner.getProperties();

        // use the getAsString method on an XmlSerializer class
        var xmlSerializer = new PrettyXmlSerializer(cleanerProperties);

        return xmlSerializer.getAsString(rootTagNode);
    }
}
