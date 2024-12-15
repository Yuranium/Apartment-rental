package ru.yuriy.propertyrental.services;

import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.SneakyThrows;
import org.antlr.v4.runtime.misc.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.yuriy.propertyrental.models.entity.Apartment;
import ru.yuriy.propertyrental.models.entity.Payment;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class EmailService
{
    private final JavaMailSender mailSender;

    private final TemplateEngine engine;

    @Getter
    private String code;

    @Value("${spring.mail.username}")
    private String email;

    private String emailTo;

    @Autowired
    public EmailService(JavaMailSender mailSender, TemplateEngine engine)
    {
        this.mailSender = mailSender;
        this.engine = engine;
    }

    @SneakyThrows
    public void sendHtmlEmail(String to)
    {
        emailTo = to;
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
        helper.setFrom(email);

        mailSender.send(mimeMessage);
    }

    public void repeatSendEmail()
    {
        if (emailTo != null && !emailTo.isEmpty())
            sendHtmlEmail(emailTo);
    }

    @SneakyThrows
    public void sendMessageLatePayment(String to, String username, List<Payment> payments)
    {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("expiredApartments", createTriple(payments));
        String htmlBody = engine.process("latePayment", context);

        helper.setTo(to);
        helper.setSubject("Уведомление о просрочке платежа");
        helper.setText(htmlBody, true);
        helper.setFrom(email);

        mailSender.send(mimeMessage);
    }

    private List<Triple<Apartment, Long, Double>> createTriple(List<Payment> payments)
    {
        return payments.stream()
                .limit(5)
                .map(payment ->
                        new Triple<>(payment.getApartment(),
                                daysOverdue(payment.getDatePayment()),
                                payment.getAmountPayment()))
                .collect(Collectors.toList());
    }

    private Long daysOverdue(Date datePayment)
    {
        LocalDate paymentDate = datePayment.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return ChronoUnit.DAYS.between(paymentDate, LocalDate.now());
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