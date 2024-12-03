# Throughput (Пропускная способность / количество сообщений в секунду)
## Simple (1 p + 1 c) (p = c)
Kafka позволяет делать больше операций как Consumer, так и Producer
1. Kafka (Consumer) = 250498.964
2. Kafka (Producer) = 758.989
3. Rabbit (Consumer) = 0.811
4. Rabbit (Producer) = 44.707

## Load balancing (3 p + 1 c) (p > c)
Kafka все равно позволяет делать больше операций как Consumer, так и Producer.
При этом количество операций в Kafka снизилось примерно в 3 раза.
1 c не успеет обработать нагрузку от 3p.
Количество операций в Rabbit также снизилось примерно в 4 раза.
1. Kafka (Consumer) = 72451.765
2. Kafka (Producer) = 256.089
3. Rabbit (Consumer) =  0.269
4. Rabbit (Producer) = 12.026

## Multiple consumers (1 p + 3 c) (p < c))
Kafka делает примерно столько же Consumer, как при Load balancing. Однако Producer выросло в 3 раза. 
У Rabbit похожая ситуация, только Producer вырос в 2 раза.
1. Kafka (Consumer) = 72049.855
2. Kafka (Producer) = 761.640
3. Rabbit (Consumer) =  0.274
4. Rabbit (Producer) = 27.633

## Load balancing + multiple consumers (3 p + 3 c) (p = c)
У Kafka Consumer лучше, чем в Load balancing или Multiple consumers по отдельности,
но Producer почти такой же, как в Load balancing.
Деградация производительности Kafka объясняется увеличением числа операций синхронизации.
У Rabbit получилось самые низкие показатели среди всего до этого.
1. Kafka (Consumer) = 113892.517
2. Kafka (Producer) = 223.832
3. Rabbit (Consumer) = 0.191
4. Rabbit (Producer) =  10.864

## Stress test (10 p + 10 c) (p = c)
Результаты стали совсем плохими как у Kafka, так и у Rabbit.
Деградация производительности Kafka объясняется увеличением числа операций синхронизации.
1. Kafka (Consumer) =  47781.814 
2. Kafka (Producer) =  80.816
3. Rabbit (Consumer) =  0.080
4. Rabbit (Producer) = 3.510

# Latency (Время отклика)

## Simple (1 p + 1 c) (p = c)
У Kafka почти нет задержки. При этом Producer у нее хуже, чем Consumer. 
У Rabbit наоборот, Consumer хуже, чем Producer.
1. Kafka (Consumer) ≈ 10⁻⁵ 
2. Kafka (Producer) = 0.001
3. Rabbit (Consumer) = 1.331
4. Rabbit (Producer) = 0.031

## Load balancing (3 p + 1 c) (p > c))
Producer у Kafka стал хуже в 3 раза. 
При этом Consumer и Producer у Rabbit стали хуже в несколько раз.
1. Kafka (Consumer) ≈ 10⁻⁵
2. Kafka (Producer) = 0.004
3. Rabbit (Consumer) = 5.714
4. Rabbit (Producer) = 0.128

## Multiple consumers (1 p + 3 c) (p < c)
Результаты у Kafka как в случае Simple.
У Rabbit результаты хуже, чем в Simple, но лучше Load balancing. 
1. Kafka (Consumer) ≈ 10⁻⁵
2. Kafka (Producer) = 0.001
3. Rabbit (Consumer) = 4.355
4. Rabbit (Producer) = 0.039

## Load balancing + multiple consumers (3 p + 3 c) (p = c)
У Kafka результаты как в Load balancing.
У Rabbit хуже, чем в Simple, но лучше чем в Load balancing и multiple consumers.
1. Kafka (Consumer) ≈ 10⁻⁵
2. Kafka (Producer) = 0.004
3. Rabbit (Consumer) = 3.569
4. Rabbit (Producer) = 0.082

## Stress test (10 p + 10 c) (p = c)
Здесь Kafka и Rabbit показали самые худшие результаты.
Деградация производительности Kafka объясняется увеличением числа операций синхронизации.
1. Kafka (Consumer) ≈ 10⁻⁴
2. Kafka (Producer) = 0.012
3. Rabbit (Consumer) = 13.020
4. Rabbit (Producer) = 0.382

# Итог
1. Kafka более производительная (позволяет выполнять больше операций + меньше время отклика).
2. Kafka показывает лучшую производительность, когда p <= c.
3. У Rabbit заметный спад производительности наблюдается даже при небольшом увеличении нагрузки.