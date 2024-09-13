package com.carlos.chat.config;

import lombok.RequiredArgsConstructor; 
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.*;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import com.carlos.chat.chat.ChatMessage;
import com.carlos.chat.chat.MessageType;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;
    
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){

        // Wrap the message
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        
        String username = headerAccessor.getSessionAttributes().get("username").toString();

        if(username != null){
            log.info("User Disconnected: " + username);
            var chatMessage = ChatMessage.builder()
                .type(MessageType.LEAVE)
                .sender(username)
                .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
        
    }
}