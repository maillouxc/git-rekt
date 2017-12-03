
package com.gitrekt.resort.model.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * This service class is used to manage sending email from the system.
 */
public class EmailService {

    private static final Properties emailConfig = new Properties();

    private static final String username = "gitrektresort@gmail.com";

    // Lol who needs security
    private static final String password = "GitGudb4uGitRekt";

    private static final String SENDER_NAME = "Git-Rekt Resort";

    private Session session;

    public EmailService() {
        readConfig();
        initSession();
    }

    private void readConfig() {
        URL propertiesFileUrl =
                getClass().getResource("/META-INF/email.properties");
        try {
            FileInputStream inputStream;
            inputStream = new FileInputStream(propertiesFileUrl.getFile());
            emailConfig.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(
                "Failed to load email properties", e
            );
        }
    }

    private void initSession() {
        session = Session.getDefaultInstance(
            emailConfig,
            new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            }
        );
    }

    /**
     * Sends an email with the provided parameters. Enough said.
     *
     * @param toAddress The recipient email address.
     * @param subject The subject line of the email.
     * @param text The text of the message body.
     *
     * @throws MessagingException
     */
    public void sendEmail(String toAddress, String subject, String text)
        throws MessagingException {
        
        Message message = new MimeMessage(session);
        InternetAddress fromAddress;
        try {
            fromAddress = new InternetAddress(
                emailConfig.getProperty("mail.from")
            );
        } catch (AddressException ex) {
            throw new RuntimeException("Programmer was lazy.");
        }
        message.setFrom(fromAddress);
        message.setRecipients(
            Message.RecipientType.TO,
            InternetAddress.parse(toAddress)
        );
        message.setSubject(subject);
        message.setText(text);

        Transport.send(message);
    }

}
