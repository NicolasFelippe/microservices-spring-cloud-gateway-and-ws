package com.barber.servicewebsocket.web.events;


import com.barber.servicewebsocket.domain.model.Message;
import com.barber.servicewebsocket.domain.redis.NewMessageEventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * {@link MessageEventPublisher} implementation which using Redis for event publishing
 *
 * @author Nicolas Felippe
 */

@Component
public class RedisMessageEventPublisherImpl implements MessageEventPublisher {

    private RedisTemplate<String, Message> messageRedisTemplate;

    public RedisMessageEventPublisherImpl(RedisTemplate<String, Message> messageRedisTemplate) {
        this.messageRedisTemplate = messageRedisTemplate;
    }

    @Override
    public void publishMessageReceived(Message message) {
        // Publish on Redis
        messageRedisTemplate.convertAndSend(
                NewMessageEventListener.EVENT_RECEIVE_MESSAGE_KEY,
                checkNotNull(message, "The received message must not be null!"));
    }

    protected void setMessageRedisTemplate(RedisTemplate<String, Message> messageRedisTemplate) {
        this.messageRedisTemplate = messageRedisTemplate;
    }

}
