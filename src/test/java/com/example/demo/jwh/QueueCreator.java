package com.example.demo.jwh;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import com.example.demo.config.KafkaConfig;
import com.example.demo.config.RabbitConfig;
import com.example.demo.queue.Consumer;
import com.example.demo.queue.Producer;
import com.example.demo.queue.Queue;
import com.example.demo.queue.QueueType;
import com.example.demo.queue.kafka.KafkaConsumer;
import com.example.demo.queue.kafka.KafkaProducer;
import com.example.demo.queue.rabbit.RabbitConsumer;
import com.example.demo.queue.rabbit.RabbitProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

public class QueueCreator {
    private ConfigurableApplicationContext kafkaContext;
    private ConfigurableApplicationContext rabbitContext;

    public Queue create(int producersCount, int consumersCount, QueueType queueType) {
        kafkaContext = new AnnotationConfigApplicationContext(KafkaConfig.class);
        rabbitContext = new AnnotationConfigApplicationContext(RabbitConfig.class);

        List<Producer> producers = new ArrayList<>();
        List<Consumer> consumers = new ArrayList<>();

        return switch (queueType) {
            case RABBITMQ -> createRabbitConfiguration(producersCount, consumersCount, consumers, producers);
            case KAFKA -> createKafkaConfiguration(producersCount, consumersCount, producers, consumers);
        };
    }

    private Queue createKafkaConfiguration(
            int producersCount,
            int consumersCount,
            List<Producer> producers,
            List<Consumer> consumers
    ) {
        KafkaTemplate<String, String> kafkaTemplate = kafkaContext.getBean(KafkaTemplate.class);

        IntStream.range(0, consumersCount)
                .forEach(i -> consumers.add(new KafkaConsumer()));
        IntStream.range(0, producersCount)
                .forEach(i -> producers.add(new KafkaProducer(kafkaTemplate)));

        return new Queue(producers, consumers);
    }

    private Queue createRabbitConfiguration(
            int producersCount,
            int consumersCount,
            List<Consumer> consumers,
            List<Producer> producers
    ) {
        RabbitTemplate rabbitTemplate = rabbitContext.getBean(RabbitTemplate.class);

        IntStream.range(0, consumersCount)
                .forEach(i -> consumers.add(new RabbitConsumer(rabbitTemplate)));
        IntStream.range(0, producersCount)
                .forEach(i -> producers.add(new RabbitProducer(rabbitTemplate)));

        return new Queue(producers, consumers);
    }

    public void clear() {
        if (rabbitContext != null) {
            rabbitContext.close();
            rabbitContext = null;
        }
        if (kafkaContext != null) {
            kafkaContext.close();
            kafkaContext = null;
        }
    }
}
