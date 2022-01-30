package com.barber.servicewebsocket.web.events;


import com.barber.servicewebsocket.domain.model.Message;

/**
 * Interface which decouples eventing implementation from controller layer
 *
 * @author  Nicolas Felippe
 *
 */
public interface MessageEventPublisher {

    void publishMessageReceived(Message message);

}
