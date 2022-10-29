package com.example.templateservice.controller;

import com.example.templateservice.mail.MineMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mail")
public class MailController {
    private final KafkaTemplate<Object, Object> template;

    public MailController(KafkaTemplate<Object, Object> template) {
        this.template = template;
    }

    @PostMapping
    public void sendDummyMail(@RequestBody MineMessage msg) {
        template.send("mail", msg);
    }
}
