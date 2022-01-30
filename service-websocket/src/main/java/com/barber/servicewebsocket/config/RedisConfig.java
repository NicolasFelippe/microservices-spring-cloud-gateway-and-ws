package com.barber.servicewebsocket.config;

import com.barber.servicewebsocket.domain.model.Message;
import com.barber.servicewebsocket.domain.redis.NewMessageEventListener;
import com.barber.servicewebsocket.web.events.NewMessageBroadcaster;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.List;

@Configuration
public class RedisConfig {

    @Bean
    public Jackson2JsonRedisSerializer<Message> messageRedisSerializer() {

        // For Java8 date serialization
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JSR310Module());
        // objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
        // objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        // objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //

        Jackson2JsonRedisSerializer<Message> serializer = new Jackson2JsonRedisSerializer<>(Message.class);
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }

    @Bean
    public RedisTemplate<String, Message> messageRedisTemplate(RedisConnectionFactory connectionFactory) {

        RedisTemplate<String, Message> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setValueSerializer(messageRedisSerializer());

        return redisTemplate;
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter(NewMessageEventListener eventListener) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(eventListener);
        adapter.setSerializer(messageRedisSerializer());
        return adapter;
    }

    @Bean
    public MessageListenerAdapter messageBroadcasterAdapter(NewMessageBroadcaster eventListener) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(eventListener);
        adapter.setSerializer(messageRedisSerializer());
        return adapter;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory,
            NewMessageEventListener eventListener,
            NewMessageBroadcaster broadcastListener) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(messageListenerAdapter(eventListener),
                List.of(new ChannelTopic(NewMessageEventListener.EVENT_RECEIVE_MESSAGE_KEY)));
        container.addMessageListener(messageBroadcasterAdapter(broadcastListener),
                List.of(new ChannelTopic(NewMessageBroadcaster.EVENT_RECEIVE_MESSAGE_KEY)));

        return container;
    }
}
