package com.barber.servicewebsocket.controller;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class WebSocketController {

    private final Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    private final Environment env;

    public WebSocketController(Environment env) {
        this.env = env;
    }

    @MessageMapping("/message")
    @SendTo("/topic/reply")
    public String processMessageFromClient(@Payload String message){
        String name = new Gson().fromJson(message, Map.class).get("name").toString();
        String appPort = env.getProperty("local.server.port");
        logger.info(appPort);
        return appPort +"-message: Hello " + name;
    }

    @MessageExceptionHandler
    @SendToUser("/topic/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }

}