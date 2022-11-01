package com.example.templateservice.config;

import com.example.templateservice.mail.MineMessage;
import com.example.templateservice.mail.Job;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.util.backoff.FixedBackOff;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@Log4j2
public class KafkaConfig {
    private final TaskExecutor exec = new SimpleAsyncTaskExecutor();
    private AtomicInteger counter = new AtomicInteger();
    private AtomicInteger job_counter = new AtomicInteger();

    @Bean
    public SeekToCurrentErrorHandler errorHandler(KafkaOperations<Object, Object> template) {
        return new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(template), new FixedBackOff(1000L, 2));
    }

    @Bean
    public NewTopic mail() {
        return new NewTopic("mail", 1, (short) 1);
    }
    
    @Bean
    public NewTopic dlt() {
        return new NewTopic("mail.DLT", 1, (short) 1);
    }


    @Bean
    public NewTopic job() {
        return new NewTopic("job", 1, (short) 1);
    }
    
    @Bean
    public NewTopic jobDlt() {
        return new NewTopic("job.DLT", 1, (short) 1);
    }


    @KafkaListener(id = "dltGroup", topics = "mail.DLT")
    public void dltListen(String in) {
        log.info("Sending Mail DLT: " + in);
    }

    @KafkaListener(id = "jobDltGroup", topics = "job.DLT")
    public void jobDltListen(String in) {
        log.info("Sending Job DLT: " + in);
    }



    @KafkaListener(id = "mail-group", topics = "mail")
    public void listen(MineMessage msg, Acknowledgment acknowledgment) {

        final int idx = counter.incrementAndGet();
        log.info("Sending mail: " + idx);

        this.exec.execute(() -> {
            try {
                Thread.sleep(3000);
                log.info("Sending mail done: "+ idx);
                acknowledgment.acknowledge();
            } catch (InterruptedException e) {
                log.error("Error during sending email", e);
            }
        });
    }

    @KafkaListener(id = "job-group", topics = "job")
    public void jobListen(Job job, Acknowledgment acknowledgment) {

        final int idx = job_counter.incrementAndGet();
        log.info("Processing job: " + idx);

        this.exec.execute(() -> {
            try {
                Thread.sleep(Math.max(job.getDuration()*1000, 3000));
                log.info("Processing done: "+ idx);
                
            } catch (InterruptedException e) {
                log.error("Error during sending email", e);
            }
            finally {
                acknowledgment.acknowledge();
            }
        });
    }
}
