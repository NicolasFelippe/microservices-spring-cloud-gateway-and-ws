package com.barber.servicewebsocket.web.model;


import com.barber.servicewebsocket.domain.model.MessageType;

/**
 * POJO which models messages on the REST layer
 *
 * @author Nicolas Felippe
 */
public class BasicMessage {

    private String content;

    private MessageType type;

    private String PortServer;

    public BasicMessage() {
    }

    public BasicMessage(String content, MessageType type) {
        this.content = content;
        this.type = type;
    }

    public String getPortServer() {
        return PortServer;
    }

    public void setPortServer(String portServer) {
        PortServer = portServer;
    }

    protected void setContent(String content) {
        this.content = content;
    }

    protected void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public MessageType getType() {
        return type;
    }

}
