package com.example.game.tasklet;

import com.example.game.mail.EmailSender;
import com.example.game.model.Badminton;
import com.example.game.service.BadmintonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BadmintonTasklet implements Tasklet {
    private static final Logger logger = LoggerFactory.getLogger(BadmintonTasklet.class);

    @Value("${mail.recipients}")
    private String mailRecipients;
    @Autowired
    BadmintonService badmintonService;

    @Autowired
    EmailSender emailSender;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        logger.info("Badminton task started");
        try {
            List<String> times = badmintonService.getAllAvailableTimes();
            Badminton badminton = badmintonService.getCurrentBadminton();
            String[] recipients = mailRecipients.split(",");
            if (!times.isEmpty() && (badminton.getId() == null || !badminton.getTimesSent().equalsIgnoreCase(times.toString()))) {
                emailSender.sendEmail(recipients, "Badminton Times", times.toString(), badminton);
            } else {
                logger.info("Times {} had been sent already", badminton.getTimesSent());
            }
        } catch (Exception e) {
            logger.info("Badminton task failed with error {}", e.getMessage());
        }
        return RepeatStatus.FINISHED;
    }
}
