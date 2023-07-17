package com.example.game.mail;

import com.example.game.model.Badminton;
import com.example.game.service.BadmintonService;
import com.example.game.service.BatchJobExecutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import static com.example.game.util.DateTimeUtility.getCurrentDate;
import static com.example.game.util.DateTimeUtility.getCurrentHour;

@Component
public class EmailSender {
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    private final BadmintonService badmintonService;
    private final BatchJobExecutionService batchJobExecutionService;

    @Autowired
    public EmailSender(JavaMailSender mailSender, MailProperties mailProperties,
                       BadmintonService badmintonService, BatchJobExecutionService batchJobExecutionService) {
        this.mailSender = mailSender;
        this.mailProperties = mailProperties;
        this.badmintonService = badmintonService;
        this.batchJobExecutionService = batchJobExecutionService;
    }

    public void sendEmail(String[] to, String subject, String body, Badminton badminton) {
        logger.info("Sending email to recipients {}", (Object) to);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getUsername());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        try {
            mailSender.send(message);
            badminton.setTimesSent(body);
            badmintonService.saveBadminton(badminton);
            logger.info("Sent email to recipients {} on {} and saved data", (Object) to, getCurrentDate());
            int hour = getCurrentHour();
            if (hour == 23) {
                batchJobExecutionService.cleanUpStaleData();
                badmintonService.cleanUpStaleData();
            }
        } catch (Exception e) {
            logger.error("Failed to send email on {} error is {}", getCurrentDate(), e.getMessage());
        }
    }

}
