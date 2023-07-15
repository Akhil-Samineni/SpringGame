package com.example.game.config;

import com.example.game.listener.JobCompletionNotificationListener;
import com.example.game.processor.PersonItemProcessor;
import com.example.game.reader.CustomReader;
import com.example.game.tasklet.BadmintonTasklet;
import com.example.game.writer.CustomWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfigTaskletClass {
    @Bean
    public Job batchJob(JobRepository jobRepository,
                        JobCompletionNotificationListener listener, Step step) {
        return new JobBuilder("batch-job-2", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step)
                .end()
                .build();
    }

    @Bean
    public Step batchStep(JobRepository jobRepository,
                          PlatformTransactionManager transactionManager) {
        return new StepBuilder("batch-step-tasklet", jobRepository)
                .tasklet(tasklet())
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public Tasklet tasklet() {
        return new BadmintonTasklet();
    }

}
