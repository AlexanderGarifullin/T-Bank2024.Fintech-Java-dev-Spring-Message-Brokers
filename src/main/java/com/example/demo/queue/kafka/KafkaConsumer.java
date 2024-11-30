package com.example.demo.queue.kafka;

import com.example.demo.queue.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer implements Consumer {

    @Override
    @KafkaListener(topics = "topic", groupId = "group_id")
    public void consumeMessage() {
        log.debug("Kafka consumer received message");
    }

    @Override
    public void stop() {
        log.info("Kafka consumer stopped");
    }
}