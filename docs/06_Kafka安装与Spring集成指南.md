# Kafka 安装与 Spring 集成指南

---

## 一、Kafka & ZooKeeper 一键启动（推荐 Docker）

在项目根目录下已有 `docker-compose.yml`，内容如下：

```yaml
services:
  zookeeper:
    image: bitnami/zookeeper:3.8
    ports:
      - "2181:2181"
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes
  kafka:
    image: bitnami/kafka:3.6
    ports:
      - "9092:9092"
    environment:
      - KAFKA_BROKER_ID=1
      - KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181
      - ALLOW_PLAINTEXT_LISTENER=yes
      - KAFKA_LISTENERS=PLAINTEXT://:9092
      - KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092
    depends_on:
      - zookeeper
```

**启动命令：**
```sh
docker-compose up -d
```

- 启动后，Kafka服务监听 `localhost:9092`，ZooKeeper监听 `localhost:2181`
- 可用 [kcat](https://github.com/edenhill/kcat) 或 [kafka-topics.sh](https://kafka.apache.org/quickstart) 工具测试

---

## 二、Spring Boot 项目依赖配置

`background/build.gradle` 已包含：
```groovy
implementation 'org.springframework.kafka:spring-kafka'
```

---

## 三、application.yml Kafka 配置

`background/src/main/resources/application.yml` 示例：
```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
```

如需自定义 groupId、序列化等，可扩展：
```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: my-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
```

---

## 四、Spring Kafka 生产者/消费者代码示例

### 1. 生产者示例
```java
@Component
public class AiProfileTaskProducer {
    private static final String TOPIC = "ai-profile-task";
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    public void sendProfileTask(Long userId, String behaviorJson) {
        try {
            String message = new ObjectMapper().writeValueAsString(Map.of("userId", userId, "behaviorJson", behaviorJson));
            kafkaTemplate.send(TOPIC, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### 2. 消费者示例
```java
@Component
public class AiProfileTaskConsumer {
    @KafkaListener(topics = "ai-profile-task", groupId = "ai-profile-task-group")
    public void consume(String message) {
        // 反序列化、业务处理
        System.out.println("收到消息: " + message);
    }
}
```

---

## 五、常见问题与排查建议

1. **端口冲突/未启动**：确保 9092 (Kafka) 和 2181 (ZooKeeper) 未被占用。
2. **Spring 连接不上 Kafka**：确认 `bootstrap-servers` 配置与实际端口一致。
3. **消息收发乱码**：建议统一使用 StringSerializer/StringDeserializer。
4. **本地与容器网络问题**：如需远程访问，调整 `KAFKA_ADVERTISED_LISTENERS`。
5. **消费组重复/消息丢失**：合理设置 groupId 和 offset 策略。

---

## 六、参考资料
- [Spring for Apache Kafka 官方文档](https://docs.spring.io/spring-kafka/docs/current/reference/html/)
- [Kafka 官方文档](https://kafka.apache.org/documentation/)
- [Bitnami Kafka 镜像文档](https://hub.docker.com/r/bitnami/kafka)

---
