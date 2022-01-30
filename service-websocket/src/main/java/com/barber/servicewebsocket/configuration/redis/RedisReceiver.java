package com.barber.servicewebsocket.configuration.redis;

import java.util.concurrent.atomic.AtomicInteger;

import com.barber.servicewebsocket.models.Greeting;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisReceiver.class);

    private final  SimpMessagingTemplate template;

    private final Gson gson = new Gson();

    private final AtomicInteger counter = new AtomicInteger();

    public RedisReceiver(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void receiveMessage(String message) {
        LOGGER.info("RedisReceiver received <" + message + ">");
        this.template.convertAndSend("/topic/greetings",
                gson.toJson(new Greeting(message)));
        counter.incrementAndGet();
    }

    public int getCount() {
        return counter.get();
    }
}