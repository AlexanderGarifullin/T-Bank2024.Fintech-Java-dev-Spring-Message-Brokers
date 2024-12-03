package com.example.demo.jwh;

import com.example.demo.queue.Consumer;
import com.example.demo.queue.Producer;
import com.example.demo.queue.Queue;
import com.example.demo.queue.QueueType;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Fork(value = 1, warmups = 1)
@Measurement(iterations = 1)
public class BenchmarkTests {

    QueueCreator queueCreator;

    // kafka

    // 1 p + 1 c
    Queue simpleKafka;

    // 3 p + 1 c
    Queue loadBalancingKafka;

    // 1 p + 3 c
    Queue multipleConsumersKafka;

    // 3 p + 3 c
    Queue loadBalancingAndMultipleConsumersKafka;

    // 10 p + 10 c
    Queue stressTestsKafka;

    // rabbit

    // 1 p + 1 c
    Queue simpleRabbit;

    // 3 p + 1 c
    Queue loadBalancingRabbit;

    // 1 p + 3 c
    Queue multipleConsumersRabbit;

    // 3 p + 3 c
    Queue loadBalancingAndMultipleConsumersRabbit;

    // 10 p + 10 c
    Queue stressTestsRabbit;

    @Setup(Level.Trial)
    public void setup() {
        queueCreator = new QueueCreator();

        // kafka
        simpleKafka = queueCreator.create(1, 1, QueueType.KAFKA);
        loadBalancingKafka = queueCreator.create(3, 1, QueueType.KAFKA);
        multipleConsumersKafka = queueCreator.create(1, 3, QueueType.KAFKA);
        loadBalancingAndMultipleConsumersKafka = queueCreator.create(3, 3, QueueType.KAFKA);
        stressTestsKafka = queueCreator.create(10, 10, QueueType.KAFKA);

        // rabbit
        simpleRabbit = queueCreator.create(1, 1, QueueType.RABBITMQ);
        loadBalancingRabbit = queueCreator.create(3, 1, QueueType.RABBITMQ);
        multipleConsumersRabbit = queueCreator.create(1, 3, QueueType.RABBITMQ);
        loadBalancingAndMultipleConsumersRabbit = queueCreator.create(3, 3, QueueType.RABBITMQ);
        stressTestsRabbit = queueCreator.create(10, 10, QueueType.RABBITMQ);
    }

    @TearDown(Level.Trial)
    public void tearDown() {

        queueCreator.clear();

        // kafka
        simpleKafka.stop();
        loadBalancingKafka.stop();
        multipleConsumersKafka.stop();
        loadBalancingAndMultipleConsumersKafka.stop();
        stressTestsKafka.stop();

        // rabbit
        simpleRabbit.stop();
        loadBalancingRabbit.stop();
        multipleConsumersRabbit.stop();
        loadBalancingAndMultipleConsumersRabbit.stop();
        stressTestsRabbit.stop();
    }

    // Latency

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void SimpleKafka_Producer_Latency() {
        simpleKafka.producers().forEach(Producer::produceMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void SimpleKafka_Consumer_Latency() {
        simpleKafka.consumers().forEach(Consumer::consumeMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LoadBalancingKafka_Producer_Latency() {
        loadBalancingAndMultipleConsumersKafka.producers().forEach(Producer::produceMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LoadBalancingKafka_Consumer_Latency() {
        loadBalancingAndMultipleConsumersKafka.consumers().forEach(Consumer::consumeMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void MultipleConsumersKafka_Producer_Latency() {
        multipleConsumersKafka.producers().forEach(Producer::produceMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void MultipleConsumersKafka_Consumer_Latency() {
        multipleConsumersKafka.consumers().forEach(Consumer::consumeMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LoadBalancingAndMultipleConsumersKafka_Producer_Latency() {
        loadBalancingAndMultipleConsumersKafka.producers().forEach(Producer::produceMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LoadBalancingAndMultipleConsumersKafka_Consumer_Latency() {
        loadBalancingAndMultipleConsumersKafka.consumers().forEach(Consumer::consumeMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void StressKafka_Producer_Latency() {
        stressTestsKafka.producers().forEach(Producer::produceMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void StressKafka_Consumer_Latency() {
        stressTestsKafka.consumers().forEach(Consumer::consumeMessage);
    }

    // rabbit

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void SimpleRabbit_Producer_Latency() {
        simpleRabbit.producers().forEach(Producer::produceMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void SimpleRabbit_Consumer_Latency() {
        simpleRabbit.consumers().forEach(Consumer::consumeMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LoadBalancingRabbit_Producer_Latency() {
        loadBalancingAndMultipleConsumersRabbit.producers().forEach(Producer::produceMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LoadBalancingRabbit_Consumer_Latency() {
        loadBalancingAndMultipleConsumersRabbit.consumers().forEach(Consumer::consumeMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void MultipleConsumersRabbit_Producer_Latency() {
        multipleConsumersRabbit.producers().forEach(Producer::produceMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void MultipleConsumersRabbit_Consumer_Latency() {
        multipleConsumersRabbit.consumers().forEach(Consumer::consumeMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LoadBalancingAndMultipleConsumersRabbit_Producer_Latency() {
        loadBalancingAndMultipleConsumersRabbit.producers().forEach(Producer::produceMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LoadBalancingAndMultipleConsumersRabbit_Consumer_Latency() {
        loadBalancingAndMultipleConsumersRabbit.consumers().forEach(Consumer::consumeMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void StressRabbit_Producer_Latency() {
        stressTestsRabbit.producers().forEach(Producer::produceMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void StressRabbit_Consumer_Latency() {
        stressTestsRabbit.consumers().forEach(Consumer::consumeMessage);
    }

    // Throughput

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void SimpleKafka_Producer_Throughput() {
        simpleKafka.producers().forEach(Producer::produceMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void SimpleKafka_Consumer_Throughput() {
        simpleKafka.consumers().forEach(Consumer::consumeMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LoadBalancingKafka_Producer_Throughput() {
        loadBalancingAndMultipleConsumersKafka.producers().forEach(Producer::produceMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LoadBalancingKafka_Consumer_Throughput() {
        loadBalancingAndMultipleConsumersKafka.consumers().forEach(Consumer::consumeMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void MultipleConsumersKafka_Producer_Throughput() {
        multipleConsumersKafka.producers().forEach(Producer::produceMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void MultipleConsumersKafka_Consumer_Throughput() {
        multipleConsumersKafka.consumers().forEach(Consumer::consumeMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LoadBalancingAndMultipleConsumersKafka_Producer_Throughput() {
        loadBalancingAndMultipleConsumersKafka.producers().forEach(Producer::produceMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LoadBalancingAndMultipleConsumersKafka_Consumer_Throughput() {
        loadBalancingAndMultipleConsumersKafka.consumers().forEach(Consumer::consumeMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void StressKafka_Producer_Throughput() {
        stressTestsKafka.producers().forEach(Producer::produceMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void StressKafka_Consumer_Throughput() {
        stressTestsKafka.consumers().forEach(Consumer::consumeMessage);
    }

    // rabbit

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void SimpleRabbit_Producer_Throughput() {
        simpleRabbit.producers().forEach(Producer::produceMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void SimpleRabbit_Consumer_Throughput() {
        simpleRabbit.consumers().forEach(Consumer::consumeMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LoadBalancingRabbit_Producer_Throughput() {
        loadBalancingAndMultipleConsumersRabbit.producers().forEach(Producer::produceMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LoadBalancingRabbit_Consumer_Throughput() {
        loadBalancingAndMultipleConsumersRabbit.consumers().forEach(Consumer::consumeMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void MultipleConsumersRabbit_Producer_Throughput() {
        multipleConsumersRabbit.producers().forEach(Producer::produceMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void MultipleConsumersRabbit_Consumer_Throughput() {
        multipleConsumersRabbit.consumers().forEach(Consumer::consumeMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LoadBalancingAndMultipleConsumersRabbit_Producer_Throughput() {
        loadBalancingAndMultipleConsumersRabbit.producers().forEach(Producer::produceMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LoadBalancingAndMultipleConsumersRabbit_Consumer_Throughput() {
        loadBalancingAndMultipleConsumersRabbit.consumers().forEach(Consumer::consumeMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void StressRabbit_Producer_Throughput() {
        stressTestsRabbit.producers().forEach(Producer::produceMessage);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void StressRabbit_Consumer_Throughput() {
        stressTestsRabbit.consumers().forEach(Consumer::consumeMessage);
    }
}
