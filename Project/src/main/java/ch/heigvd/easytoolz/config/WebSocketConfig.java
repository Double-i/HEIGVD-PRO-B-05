package ch.heigvd.easytoolz.config;

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
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/secured/user/queue/specific-user/");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/secured/room").setAllowedOrigins("*");
        registry.addEndpoint("/secured/room").setAllowedOrigins("*").withSockJS();


        /*registry.addEndpoint("/chat")
                .setAllowedOrigins("*")
                .setHandshakeHandler(new DefaultHandshakeHandler()
                {
                    public boolean beforeHandshake(
                            ServerHttpRequest request,
                            ServerHttpResponse response,
                            WebSocketHandler webSocketHandler,
                            Map Attributes) throws Exception{

                        if(request instanceof ServletServerHttpRequest)
                        {
                            ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;

                            HttpSession session = serverHttpRequest.getServletRequest().getSession();
                            Attributes.put("sessionId",session.getId());
                        }
                        return true;
                    }

                }).withSockJS();*/
    }

}
