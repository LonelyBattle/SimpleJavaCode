package com.zs.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "demo-topic", groupId = "my-group")
    public void consume(String message) {
        System.out.println("📥 接收到消息: " + message);
    }
}
