package com.zs.controller;

import com.zs.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaProducer producer;

    @GetMapping("/send")
    public String sendMessage(@RequestParam String msg) {
        producer.sendMessage(msg);
        return "消息已发送：" + msg;
    }
}
