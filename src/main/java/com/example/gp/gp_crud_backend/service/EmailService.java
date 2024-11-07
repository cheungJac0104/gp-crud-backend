package com.example.gp.gp_crud_backend.service;

import jakarta.ejb.Stateless;
import jakarta.enterprise.context.Dependent;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


@Stateless
@Dependent
public class EmailService {

    public String replacePlaceholder(String template, String placeholder, String value) {
        return template.replace(placeholder, value);
    }

    public void sendAccountCreatedEmail(String to, String subject, String password) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // Use App Password instead of regular password
        final String username = "t7105278@gmail.com";
        final String appPassword = "ntjkbbnrxhrmhnco"; // Generate this from Google Account

        try {
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, appPassword);
                }
            });

            // Enable debug mode if needed
            // session.setDebug(true);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);

            // Load HTML template
            String htmlBody = loadHtmlTemplate("/account_created_notice.html"); // Update path for deployment
            htmlBody = replacePlaceholder(htmlBody, "${password}", password);

            message.setContent(htmlBody, "text/html; charset=utf-8");

            Transport.send(message);
        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
            e.printStackTrace(); // Add this for better debugging
        }
    }

    // Update the loadHtmlTemplate method for deployment
private String loadHtmlTemplate(String templatePath) {
    try {
        // Use ClassLoader to load the template from the deployment
        InputStream inputStream = getClass().getResourceAsStream(templatePath);
        if (inputStream == null) {
            throw new IOException("Template file not found: " + templatePath);
        }
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    } catch (IOException e) {
        System.out.println("Error loading template: " + e.getMessage());
        e.printStackTrace();
        return ""; // Or handle the error appropriately
    }
}
}

