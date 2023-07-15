/*
package com.example.game.config;

import com.example.game.listener.JobCompletionNotificationListener;
import com.example.game.processor.PersonItemProcessor;
import com.example.game.reader.CustomReader;
import com.example.game.writer.CustomWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

//working but commented to pick another batch
@Configuration
public class BatchConfigClass {
    @Bean
    public Job job(JobRepository jobRepository,
                   JobCompletionNotificationListener listener, Step step) {
        return new JobBuilder("batch-job-1", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step)
                .end()
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository,
                     PlatformTransactionManager transactionManager) {
        return new StepBuilder("batch-step-reader-processor-writer", jobRepository)
                .<String, String>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public CustomReader reader() {
        return new CustomReader();
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public CustomWriter writer() {
        return new CustomWriter();
    }


}
*/
