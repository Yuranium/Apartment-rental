package ru.yuriy.propertyrental.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Random;

@Service
public class EmailService
{
    private final JavaMailSender mailSender;

    private final TemplateEngine engine;

    @Getter
    private String code;

    @Autowired
    public EmailService(JavaMailSender mailSender, TemplateEngine engine) {
        this.mailSender = mailSender;
        this.engine = engine;
    }


    @SneakyThrows
    public void sendHtmlEmail(String to)
    {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        Context context = new Context();
        String subject = "Подтверждение регистрации";
        context.setVariable("subject", subject);
        String message = "Пожалуйста, введите данный код для подтверждения регистрации";
        context.setVariable("message", message);
        context.setVariable("confirmCode", generateConfirmCode());
        String htmlBody = engine.process("emailTemplate", context);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        mailSender.send(mimeMessage);
    }

    private String generateConfirmCode()
    {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++)
            builder.append(new Random().nextInt(1, 9));
        code = builder.toString();
        return code;
    }
}