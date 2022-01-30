package com.barber.servicewebsocket.domain.service;


import com.barber.servicewebsocket.domain.model.Message;

import java.util.Collection;


/**
 * Interface which defines domain methods connected to {@link Message} objects
 *
 * @author Nicolas Felippe
 */
public interface MessageService {

    /**
     * Saves a new {@link Message} with a generated ID. Ignores the id property of the message parameter.
     *
     * @param message Saved message
     * @return The id of the saved {@link Message}
     */
    Message create(Message message);

    /**
     * Finds all the messages.
     */
    Collection<Message> findAll();

    /**
     * Returns a {@link Message} by id.
     *
     * @param id The queried id
     * @return The message with the given id or <code>null</code> if not found.
     */
    Message findById(String id);

    /**
     * Deletes the message with the given id.
     *
     * @throws IllegalStateException if no message was found with the given id.
     */
    void delete(String id);

}