package com.example.game.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Component
public class CustomReader implements ItemReader<String> {
    @Override
    public String read() {
        System.out.println("read");
        return "reader";
    }
}
