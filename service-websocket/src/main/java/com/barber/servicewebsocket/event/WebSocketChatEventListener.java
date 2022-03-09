package com.barber.servicewebsocket.event;


import com.barber.servicewebsocket.model.Message;
import com.barber.servicewebsocket.model.MessageType;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketChatEventListener {
    private final SimpMessageSendingOperations messagingTemplate;
    private final Environment env;

    public WebSocketChatEventListener(SimpMessageSendingOperations messagingTemplate, Environment env) {
        this.messagingTemplate = messagingTemplate;
        this.env = env;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        System.out.println("Received a new web socket connection");
    }
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            Message chatMessage = Message.MessageBuilder.empty()
                                                        .withPortServer(env.getProperty("local.server.port"))
                                                        .withType(MessageType.LEAVE.toString())
                                                        .sentBy(username)
                                                        .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}