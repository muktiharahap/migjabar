package com.github.muktiharahap.migjabar.service;

import com.github.muktiharahap.migjabar.domain.User;
import com.github.muktiharahap.migjabar.config.Constants;
import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.util.Locale;

/**
 * @author mukti on 10/4/2017.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER_NAME = "userName";

    private static final String BASE_URL = "baseUrl";

    private static final String PASSWORD = "password";

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    public MailService(JavaMailSender javaMailSender,
                       MessageSource messageSource, SpringTemplateEngine templateEngine) {

        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom("admin@migjabar.com");
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.warn("Email could not be sent to user '{}'", to, e);
            } else {
                log.warn("Email could not be sent to user '{}': {}", to, e.getMessage());
            }
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String passwrod, String templateName) {
        Locale locale = Locale.forLanguageTag(Constants.DEFAULT_LANGUAGE);
        Context context = new Context(locale);
        context.setVariable(USER_NAME, user.getNik());
        context.setVariable(BASE_URL, "https://www.google.co.id");
        context.setVariable(PASSWORD, passwrod);
        String content = templateEngine.process(templateName, context);
        sendEmail(user.getEmail(), "test email", content, false, true);
    }

    @Async
    public void sendActivationEmail(User user, String password) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, password, "activationEmail");
    }
}
