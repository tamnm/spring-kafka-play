package com.example.templateservice.service;

import com.example.templateservice.mail.MineMessage;

public interface MailSender {
    void send(MineMessage message);
}
