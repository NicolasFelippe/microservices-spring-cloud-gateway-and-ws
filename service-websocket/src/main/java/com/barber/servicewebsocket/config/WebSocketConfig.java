package com.barber.servicewebsocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app")
                .enableStompBrokerRelay("/topic")
                .setRelayHost("localhost").setRelayPort(61613 )
                .setClientLogin("guest")
                .setClientPasscode("guest");
    }

    @EventListener
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        Object obj = event.getMessage();
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-rpa")
                .setAllowedOriginPatterns("*")
                .withSockJS()
                .setInterceptors(new HttpSessionHandshakeInterceptor());
    }

}