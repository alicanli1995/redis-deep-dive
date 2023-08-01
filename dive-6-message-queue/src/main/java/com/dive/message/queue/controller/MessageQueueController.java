package com.dive.message.queue.controller;

import com.dive.message.queue.queue.RedisMessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageQueueController {

    private final RedisMessagePublisher messagePublisher;

    @PostMapping("/publish")
    public void publishMessage(@RequestBody String message) {
        messagePublisher.publish(message);
    }
}
