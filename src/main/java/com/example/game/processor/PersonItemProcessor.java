package com.example.game.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<String, String> {
    private static final Logger logger = LoggerFactory.getLogger(PersonItemProcessor.class);


    @Override
    public String process(final String person) {
        System.out.println("processor");
        return "String";
    }

}