package com.zsd.celeste;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class MailTest {
    @Autowired
    private JavaMailSender mailSender;

//    @Test
    public void sendSimpleEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        String to = "2135978695@qq.com";
        String subject = "subject";
        String text = "text";
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("cele212024@163.com");
        mailSender.send(message);
    }
}
