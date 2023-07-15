package com.example.game.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class CustomWriter implements ItemWriter<String> {

    @Override
    public void write(Chunk<? extends String> chunk) {
        System.out.println("writer");
    }
}
