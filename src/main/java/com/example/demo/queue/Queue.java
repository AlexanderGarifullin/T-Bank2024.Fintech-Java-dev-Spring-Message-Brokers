package com.example.demo.queue;

import java.util.List;

public record Queue(List<Producer> producers, List<Consumer> consumers) {

    public void start() {
        producers.forEach(Producer::produceMessage);
        consumers.forEach(Consumer::consumeMessage);
    }

    public void stop() {
        producers.forEach(Producer::stop);
        consumers.forEach(Consumer::stop);
    }
}
