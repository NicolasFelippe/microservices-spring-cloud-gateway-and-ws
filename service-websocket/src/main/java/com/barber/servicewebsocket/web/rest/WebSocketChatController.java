package com.barber.servicewebsocket.web.rest;

import com.barber.servicewebsocket.web.model.BasicMessage;
import com.barber.servicewebsocket.web.model.Computer;
import com.barber.servicewebsocket.web.model.PingPongDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketChatController {

    private final Environment env;

    public WebSocketChatController(Environment env) {
        this.env = env;
    }

    @Autowired
    private SimpMessagingTemplate brokerMessagingTemplate;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/general")
    public BasicMessage sendMessage(@Payload BasicMessage webSocketChatMessage) {
        webSocketChatMessage.setPortServer(env.getProperty("local.server.port"));
        return webSocketChatMessage;
    }

    @MessageMapping("/ping/{tokenIntegration}/{clientId}")
    @SendTo("/topic/pong.{tokenIntegration}.{clientId}")
    public BasicMessage ping(@DestinationVariable String tokenIntegration,
                             @DestinationVariable String clientId,
                             PingPongDTO pingPong,
                             SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", pingPong.getUsername());
        String payload = "PONG RUNTIME - PORT:" + env.getProperty("local.server.port");
        BasicMessage webSocketChatMessage = new BasicMessage(payload, "CHAT");
        webSocketChatMessage.setPortServer(env.getProperty("local.server.port"));
        webSocketChatMessage.setSender(pingPong.getUsername());
        return webSocketChatMessage;
    }

    @MessageMapping("/connect/{tokenIntegration}")
    @SendTo("/topic/public")
    public BasicMessage connect(@DestinationVariable String tokenIntegration, Computer computer, @Header("simpSessionId") String sessionId) {
        String payload = "RUNTIME CONNECTED CHAT - PORT:" + env.getProperty("local.server.port");
        BasicMessage webSocketChatMessage = new BasicMessage(payload, "CHAT");
        webSocketChatMessage.setPortServer(env.getProperty("local.server.port"));
        webSocketChatMessage.setSender(computer.getName());
        return webSocketChatMessage;
    }

    @MessageMapping("/chat.newUser")
    @SendTo("/topic/general")
    public BasicMessage newUser(@Payload BasicMessage webSocketChatMessage,
                                SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", webSocketChatMessage.getSender());
        webSocketChatMessage.setPortServer(env.getProperty("local.server.port"));
        return webSocketChatMessage;
    }
}