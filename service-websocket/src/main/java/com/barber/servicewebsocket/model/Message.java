package com.barber.servicewebsocket.model;

import com.barber.servicewebsocket.web.model.BasicMessage;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;

public class Message extends BasicMessage {

    private String id = UUID.randomUUID().toString();

    private LocalDateTime date = LocalDateTime.now();

    private String sender;

    private Message() {

    }

    private Message(String content, String type, String sender) {
        super(content, type);
        this.sender = sender;
        date = LocalDateTime.now();
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getSender() {
        return sender;
    }

    public String getId() {
        return id;
    }


    public static class MessageBuilder {

        private final Message message;

        private MessageBuilder(Message message) {
            this.message = message;
        }

        public MessageBuilder withContent(String content) {
            message.setContent(content);
            return this;
        }

        public MessageBuilder withType(String type) {
            message.setType(type);
            return this;
        }

        public MessageBuilder withPortServer(String port) {
            message.setPortServer(port);
            return this;
        }


        public MessageBuilder sentBy(String sender) {
            message.sender = sender;
            return this;
        }

        public static MessageBuilder empty() {
            return new MessageBuilder(new Message());
        }

        public static MessageBuilder copyOf(Message message) {
            Message newMessage = new Message();
            newMessage.setContent(message.getContent());
            newMessage.setType(message.getType());
            newMessage.date = message.getDate();
            newMessage.sender = message.getSender();
            newMessage.id = message.getId();

            return new MessageBuilder(newMessage);
        }

        public static MessageBuilder randomInfo() {
            Message newMessage = new Message();
            newMessage.setContent(randomAlphabetic(200));
            newMessage.setType(MessageType.INFO.toString());
            newMessage.sender = randomAlphabetic(20);

            return new MessageBuilder(newMessage);
        }

        public Message build() {
            return message;
        }

    }

}
