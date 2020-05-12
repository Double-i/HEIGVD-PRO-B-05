package ch.heigvd.easytoolz.config;

import ch.heigvd.easytoolz.services.interfaces.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.w3c.dom.Attr;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    AuthenticationService service;


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/secured/user/queue/loan-room");
        config.setUserDestinationPrefix("/secured/user");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        class Handshake extends DefaultHandshakeHandler
        {
            @Override
            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                return new StompPrincipal(UUID.randomUUID().toString());
            }
        }

        registry.addEndpoint("/EZStomp").setAllowedOrigins("*").setHandshakeHandler(new Handshake());
        registry.addEndpoint("/EZStomp").setAllowedOrigins("*").setHandshakeHandler(new Handshake()).withSockJS();

    }

}
