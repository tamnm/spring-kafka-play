package com.example.templateservice;

import com.example.templateservice.config.TemplateConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {TemplateConfig.class})
class TemplateEngineTests {
    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void engine_must_not_be_null(){
        assertThat(templateEngine).isNotNull();
    }

    @Test
    void generate_static_html(){
        var context = new Context();
        final var staticHtml = "<h2>hello world</h2>";

        var response = templateEngine.process(staticHtml,context);
        assertThat(response).isEqualTo(staticHtml);
    }

    @Test
    void generate_html_simple_variable(){
        var context = new Context();
        context.setVariable("name", "world");
        final var template = "<h2>hello [[${name}]]</h2>";

        var response = templateEngine.process(template,context);
        assertThat(response).isEqualTo("<h2>hello world</h2>");
    }
}
