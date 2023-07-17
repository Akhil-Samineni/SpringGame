package com.example.game.service;

import com.example.game.model.Badminton;
import com.example.game.repository.BadmintonRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.example.game.util.ConstantsUtil.*;
import static com.example.game.util.DateTimeUtility.*;

@Component
public class BadmintonService {
    private static final Logger logger = LoggerFactory.getLogger(BadmintonService.class);
    @Autowired
    private BadmintonRepository badmintonRepository;

    public List<String> getAllAvailableTimes() {
        logger.info("Getting available times for {} at hour {} from {} to {}", getCurrentDate(), getCurrentHour(), startTime, endTime);
        List<String> availableTimes = new ArrayList<>();
        HashMap<String, Integer> availableTimesCount = new HashMap<>();
        String url = "https://pbc.pike13.com/locations/dfw-badminton-center/appointments/238818";
        try {
            // Send a GET request to the URL and retrieve the HTML content
            Document document = Jsoup.connect(url).get();
            // Get the current date
            LocalDate currentDate = LocalDate.now();

            // Format the current date as 'yyyy-MM-dd'
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
            String formattedDate = currentDate.format(formatter);

            // Find the <a> tags containing the available times
            Elements aTags = document.select(String.format("a.button[href*='date=%s'] strong", formattedDate));

            // Extract and print the available times
            for (Element strongTag : aTags) {
                String time = strongTag.text();
                String[] startAndEnd = time.split("-");
                if (startAndEnd.length == 2 && time.contains(PM)) {
                    startAndEnd[0] = startAndEnd[0].replaceAll(":00pm", "");
                    startAndEnd[1] = startAndEnd[1].replaceAll(":00pm", "");
                    int start = Integer.parseInt(startAndEnd[0]);
                    int end = Integer.parseInt(startAndEnd[1]);
                    if (start >= startTime && end <= endTime) {
                        if (availableTimesCount.containsKey(time)) {
                            int count = availableTimesCount.get(time) + 1;
                            availableTimesCount.put(time, count);
                        } else {
                            availableTimesCount.put(time, 1);
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.info("Error while getting available times for {} at hour {} from {} to {} Error is {}", getCurrentDate(), getCurrentHour(), startTime, endTime, e.getMessage());
        }
        if (availableTimesCount.isEmpty()) {
            logger.info("No available times for {} at hour {} from {} to {}", getCurrentDate(), getCurrentHour(), startTime, endTime);
        } else {
            availableTimesCount.keySet().forEach(key -> availableTimes.add(availableTimesCount.get(key) + " courts available on " + getCurrentDate() + " at " + key));
        }
        return availableTimes;
    }

    public Badminton getCurrentBadminton() {
        Optional<Badminton> existingOne = badmintonRepository.getByCurrentDate(getCurrentDate());
        return existingOne.orElseGet(this::getNewBadminton);
    }

    public void saveBadminton(Badminton badminton) {
        try {
            badmintonRepository.save(badminton);
        } catch (Exception e) {
            logger.error("Error while saving data {}  Error is {}", badminton, e.getMessage());
        }
    }

    public Badminton getNewBadminton() {
        Badminton badminton = new Badminton();
        badminton.setBookingDate(getCurrentDate());
        return badminton;
    }

    public void cleanUpStaleData() {
        logger.info("Cleaning up badminton stale data for date {}", getCurrentDate());
        try {
            Optional<List<Badminton>> badmintonList = badmintonRepository.getStaleData(getCurrentDate());
            badmintonList.ifPresent(badmintons -> badmintonRepository.deleteAll(badmintons));
            logger.info("Cleaned up badminton stale data on date {}", getCurrentDate());
        } catch (Exception e) {
            logger.info("Error while cleaning up badminton stale data on date {} Error is {}",getCurrentDate(), e.getMessage());
        }
    }
}
