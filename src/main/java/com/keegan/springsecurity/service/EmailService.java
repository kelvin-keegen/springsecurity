package com.keegan.springsecurity.service;

import com.keegan.springsecurity.email.EmailSender;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender javaMailSender;


    @Override
    public void send(String to, String email) {

        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,"utf-8");
            helper.setText(email,true);
            helper.setTo(to);
            helper.setSubject("Confirm you Email please");
            helper.setFrom("no_reply_profile@kmail.com");
            javaMailSender.send(message);

        }  catch (MessagingException e) {
            logger.error("Unable to send email");
            throw new IllegalStateException("Unable to send email");
        }


    }
}
