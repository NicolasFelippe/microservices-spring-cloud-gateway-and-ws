package com.barber.servicewebsocket.web.model;


/**
 * POJO which models messages on the REST layer
 *
 * @author Nicolas Felippe
 */
public class BasicMessage {

    private String content;

    private String sender;

    private String type;

    private String portServer;

    public BasicMessage() {
    }

    public BasicMessage(String content, String type) {
        this.content = content;
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getPortServer() {
        return portServer;
    }

    public void setPortServer(String portServer) {
        this.portServer = portServer;
    }

    protected void setContent(String content) {
        this.content = content;
    }

    protected void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

}
