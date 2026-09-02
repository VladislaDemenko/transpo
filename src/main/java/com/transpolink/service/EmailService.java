package com.transpolink.service;

import com.transpolink.dto.CalculatorRequest;
import com.transpolink.dto.ConsultationRequest;
import com.transpolink.dto.DirectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${app.email.recipient}")
    private String recipientEmail;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    /**
     * Отправка заявки из калькулятора
     */
    public void sendCalculatorRequest(CalculatorRequest request) {
        try {
            Context context = new Context();
            context.setVariable("type", "📦 Расчёт стоимости перевозки");
            context.setVariable("from", request.getFrom());
            context.setVariable("to", request.getTo());
            context.setVariable("weight", request.getWeight());
            context.setVariable("volume", request.getVolume());
            context.setVariable("clientName", request.getClientName());
            context.setVariable("phone", request.getPhone());
            context.setVariable("time", LocalDateTime.now().format(FORMATTER));
            context.setVariable("isCalculator", true);
            context.setVariable("isConsultation", false);

            String html = templateEngine.process("email-template", context);
            sendEmail("Новая заявка на расчёт стоимости", html);

            log.info("Заявка из калькулятора отправлена на почту");
        } catch (Exception e) {
            log.error("Ошибка при отправке заявки из калькулятора", e);
            throw new RuntimeException("Не удалось отправить заявку", e);
        }
    }

    /**
     * Отправка заявки на консультацию
     */
    public void sendConsultationRequest(ConsultationRequest request) {
        try {
            Context context = new Context();
            context.setVariable("type", "📞 Заявка на консультацию");
            context.setVariable("clientName", request.getClientName());
            context.setVariable("phone", request.getPhone());
            context.setVariable("email", request.getEmail());
            context.setVariable("time", LocalDateTime.now().format(FORMATTER));
            context.setVariable("isCalculator", false);
            context.setVariable("isConsultation", true);

            String html = templateEngine.process("email-template", context);
            sendEmail("📞 Новая заявка на консультацию", html);

            log.info("✅ Заявка на консультацию отправлена на почту");
        } catch (Exception e) {
            log.error("❌ Ошибка при отправке заявки на консультацию", e);
            throw new RuntimeException("Не удалось отправить заявку", e);
        }
    }

    /**
     * Отправка прямого запроса
     */
    public void sendDirectRequest(DirectRequest request) {
        try {
            Context context = new Context();
            context.setVariable("type", "✉️ Прямой запрос с контактов");
            context.setVariable("fullName", request.getFullName());
            context.setVariable("email", request.getEmail());
            context.setVariable("organization", request.getOrganization());
            context.setVariable("serviceType", request.getServiceType());
            context.setVariable("message", request.getMessage());
            context.setVariable("time", LocalDateTime.now().format(FORMATTER));
            context.setVariable("isDirect", true);
            context.setVariable("isCalculator", false);
            context.setVariable("isConsultation", false);

            String html = templateEngine.process("email-template", context);
            sendEmail("✉️ Новый прямой запрос с сайта", html);

            log.info("✅ Прямой запрос отправлен на почту");
        } catch (Exception e) {
            log.error("❌ Ошибка при отправке прямого запроса", e);
            throw new RuntimeException("Не удалось отправить запрос", e);
        }
    }

    /**
     * Отправка письма
     */
    private void sendEmail(String subject, String htmlContent) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(recipientEmail);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
        log.info("📧 Письмо отправлено на {}", recipientEmail);
    }
}