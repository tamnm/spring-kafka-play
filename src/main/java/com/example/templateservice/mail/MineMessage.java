package com.example.templateservice.mail;

import lombok.*;

import java.util.Optional;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class MineMessage {
    private String from;
    private String to;
    private String subject;
    private String text;

    @Override
    public String toString() {
        return "MineMessage{" +
                "from='" + String.valueOf(from) + '\'' +
                ", to='" + String.valueOf(to) + '\'' +
                ", subject='" + String.valueOf(subject) + '\'' +
                ", text='" + String.valueOf(text) + '\'' +
                '}';
    }
}
