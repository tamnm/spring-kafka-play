package com.example.templateservice.service;

import com.example.templateservice.config.TemplateConfig;
import com.example.templateservice.exception.TemplateGeneratingException;
import com.example.templateservice.exception.TemplateLoadingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = {TemplateConfig.class, TemplateServiceImpl.class})
public class TemplateServiceTests {

    @Autowired
    TemplateService templateService;

    @Test
    public void serviceNotEmpty(){

        assertThat(templateService).isNotNull();
    }

    @Test
    void addTemplate() throws TemplateLoadingException {
        templateService.addTemplate("t1","hello");
    }

    @Test
    void generateText() throws IOException, TemplateLoadingException, TemplateGeneratingException {
        templateService.addTemplate("t1","hello [[${user}]]");

        try(var outputStream = new ByteArrayOutputStream()){
            var variables = new HashMap<String, Object>()
            {
                {put("user","me");}
            };

            templateService.generateText("t1", variables, outputStream);
            var result = outputStream.toString(StandardCharsets.UTF_8);

            assertThat(result).isNotNull().isEqualTo("hello me");
        }
    }
}
