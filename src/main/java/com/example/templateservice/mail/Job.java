package com.example.templateservice.mail;

import lombok.*;

import java.util.Optional;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Job {
    //Duration in second
    private int duration;

    @Override
    public String toString() {
        return "MineMessage{" +
                "duration='" + String.valueOf(duration) + '\'' +
                '}';
    }
}
