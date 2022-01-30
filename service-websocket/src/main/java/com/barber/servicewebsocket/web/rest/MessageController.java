package com.barber.servicewebsocket.web.rest;


import com.barber.servicewebsocket.domain.model.Message;
import com.barber.servicewebsocket.domain.service.MessageService;
import com.barber.servicewebsocket.web.events.MessageEventPublisher;
import com.barber.servicewebsocket.web.model.BasicMessage;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Collection;


@Controller
@RequestMapping("/api")
@CrossOrigin("*")
public class MessageController {

    private MessageService messageService;
    private MessageEventPublisher messageEventPublisher;
    private final Environment env;

    public MessageController(MessageService messageService,
                             MessageEventPublisher messageEventPublisher,
                             Environment env) {
        this.messageService = messageService;
        this.messageEventPublisher = messageEventPublisher;
        this.env = env;
    }

    // TODO error handling

    /**
     * Rest resource for message broadcast
     */
    @PostMapping(value = "/message")
    public ResponseEntity<Void> broadcast(@RequestBody BasicMessage message, HttpServletRequest request) {
        message.setPortServer(env.getProperty("local.server.port"));
        Message receivedMessage = createBuilderFromBasicMessage(message)
                .sentBy(request.getRemoteAddr())
                .build();

        // Publish message received event
        messageEventPublisher.publishMessageReceived(receivedMessage);

        return ResponseEntity
                .created(URI.create(getServerUrl(request) + "/api/message/" + receivedMessage.getId()))
                .build();
    }

    /**
     * Rest resource for querying all the persisted messages
     */
    @GetMapping(value = "/message")
    public ResponseEntity<Collection<Message>> getMessages() {

        Collection<Message> findAll = messageService.findAll();

        return ResponseEntity.ok(findAll);
    }

    /**
     * Rest resource for querying all the persisted messages
     */
    @DeleteMapping(value = "/message/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        messageService.delete(id);

        return ResponseEntity.ok().build();
    }


    /**
     * WebSocket channel for receiving {@link BasicMessage} object from websocket clients
     */
    @MessageMapping("/broadcast")
    public void socketBroadcast(BasicMessage message, SimpMessageHeaderAccessor headerAccessor) {
        // TODO read client address from websocket session
        String sessionId = headerAccessor.getSessionId();
        message.setPortServer(env.getProperty("local.server.port"));
        messageEventPublisher.publishMessageReceived(createBuilderFromBasicMessage(message).sentBy(sessionId).build());
    }

    private Message.MessageBuilder createBuilderFromBasicMessage(BasicMessage message) {
        return Message.MessageBuilder.empty()
                .withContent(message.getContent())
                .withType(message.getType())
                .withPortServer(message.getPortServer());
    }

    private String getServerUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }

    protected void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    protected void setMessageEventPublisher(MessageEventPublisher messageEventPublisher) {
        this.messageEventPublisher = messageEventPublisher;
    }

}
