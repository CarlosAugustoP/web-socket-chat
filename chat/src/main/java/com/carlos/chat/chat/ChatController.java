package com.carlos.chat.chat;

import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")   
    public ChatMessage sendMessage(
        @Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
        @Payload ChatMessage chatMessage, 
        SimpMessageHeaderAccessor headerAccessor) {
        // Adds username in web socket session
            headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
        
    }
}


/*
 * The ChatController Class is a Spring Controller that
 * handles WebSocket messages.
 * Fistly, MessageMapping annotation is used to map the incoming
 * WebSocket messages to the sendMessage() method.
 * The SendTo annotation is used to broadcast the message to all
 * subscribers of the /topic/public channel.
 * The addUser() method is used to add the username in the WebSocket
 * And the Payload annotation is used to extract the payload of the message.
 * The SimpMessageHeaderAccessor is used to extract the username from the message,
 * locates in the header of the payload.
 */