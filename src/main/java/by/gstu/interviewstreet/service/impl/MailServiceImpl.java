package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService {
    private static final Logger LOG = LoggerFactory.getLogger(MailServiceImpl.class);

    private static final String HOST_KEY = "mail.smtp.host";
    private static final String PORT_KEY = "mail.smtp.port";
    private static final String USERNAME_KEY = "mail.user";
    private static final String PASSWORD_KEY = "mail.password";

    private static final String HOST_VALUE = "localhost";
    private static final String PORT_VALUE = "25";
    private static final String USERNAME_VALUE = "admin";
    private static final String PASSWORD_VALUE = "admin";

    @Override
    public void send(String from, String to) {
        Properties properties = System.getProperties();

        properties.setProperty(HOST_KEY, HOST_VALUE);
        properties.setProperty(PORT_KEY, PORT_VALUE);
        properties.setProperty(USERNAME_KEY, USERNAME_VALUE);
        properties.setProperty(PASSWORD_KEY, PASSWORD_VALUE);

        Session session = Session.getDefaultInstance(properties);
        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("This is the Subject Line!");
            message.setText("This is actual message");

            Transport.send(message);
        } catch (MessagingException e) {
            LOG.warn("Mail from =" + from + " to=" + to + " not sent.", e);
        }
    }
}
