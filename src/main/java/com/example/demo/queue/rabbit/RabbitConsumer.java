package com.example.demo.queue.rabbit;

import com.example.demo.queue.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitConsumer implements Consumer {

    private final RabbitTemplate rabbitTemplate;

    @Override
    @RabbitListener(queues = "topic")
    public void consumeMessage() {
        rabbitTemplate.receive("topic");
        log.debug("Rabbit consumer received message");
    }

    @Override
    public void stop() {
        rabbitTemplate.getConnectionFactory().resetConnection();
        rabbitTemplate.destroy();
        log.info("Rabbit consumer stopped");
    }
}