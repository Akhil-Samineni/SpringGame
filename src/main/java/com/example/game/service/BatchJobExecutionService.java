package com.example.game.service;

import com.example.game.model.Badminton;
import com.example.game.repository.BadmintonRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.example.game.util.ConstantsUtil.*;

@Component
public class BatchJobExecutionService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(BatchJobExecutionService.class);

    public void cleanUpStaleData() {
        logger.info("Cleaning up data from batch job tables");
        try {
            jdbcTemplate.execute("DELETE FROM BATCH_JOB_EXECUTION where STATUS = 'COMPLETED'");
            logger.info("Cleaned up data from batch job tables");
        } catch (Exception e) {
            logger.info("Error cleaning up data from batch job tables Error is {}", e.getMessage());
        }
    }
}
