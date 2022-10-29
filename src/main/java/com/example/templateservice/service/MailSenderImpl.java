package com.example.templateservice.service;

import com.example.templateservice.mail.MineMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MailSenderImpl implements MailSender {
    @Override
    public void send(MineMessage message) {
        log.info(message.toString());
    }
}
