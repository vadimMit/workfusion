package com.workfusion.assign.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    public Session setupPropertiesAndInitSession() {
        Properties properties = System.getProperties();

        String host = "smtp.mail.ru";

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        return Session.getInstance(properties, new javax.mail.Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("vadimmitsiai@mail.ru", "Gn4Nv2Mt73fs7v23mS6t");

            }

        });
    }

    public String sendEmail(Session session) {
        String to = "tuk.tuk.rpa@gmail.com";
        String from = "vadimmitsiai@mail.ru";
        String status = "";

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Hello world botTask");

            // Now set the actual message
            message.setText("Hello world");

            // Send message
            Transport.send(message);
            status = "Sent message successfully";
        } catch (MessagingException mex) {
            status = "Sent message unsuccessfully";
        }
        return status;
    }
}
