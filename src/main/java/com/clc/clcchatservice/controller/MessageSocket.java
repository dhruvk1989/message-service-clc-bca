package com.clc.clcchatservice.controller;

import com.clc.clcchatservice.entity.Message;
import com.clc.clcchatservice.entity.Project;
import com.clc.clcchatservice.entity.User;
import com.clc.clcchatservice.model.MessageRequest;
import com.clc.clcchatservice.model.MessageResponse;
import com.clc.clcchatservice.repo.MessageRepo;
import com.clc.clcchatservice.repo.ProjectRepo;
import com.clc.clcchatservice.service.AuthHandler;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class MessageSocket {

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private AuthHandler authHandler;


    @MessageMapping("/chat/{projectId}")
    @SendTo("/topic/{projectId}")
    public MessageResponse handleChatMessage(@Payload MessageRequest message, @DestinationVariable Long projectId) throws Exception {

        String authorization = "Bearer " + message.getJwt();
        boolean authorized = false;

        User user = null;

        if(authorization != null && authorization.startsWith("Bearer ")) {
            try {
                List response = authHandler.authHandler(authorization.substring(7));
                authorized = (boolean) response.get(0);
                user = (User) response.get(1);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                throw new Exception(e.getMessage());
            }catch (Exception e){
                e.printStackTrace();
                if(e.getMessage().startsWith("401")){
                    throw new Exception(e.getMessage());
                }else{
                    throw new Exception(e.getMessage());
                }
            }
        }

        if(!authorized){
            throw new Exception("UNAUTHORIZED");
        }

        Project project = projectRepo.findById(projectId).get();

        Message messageEntity = new Message();
        messageEntity.setMessage(message.getMessage());
        messageEntity.setProjects(project);
        messageEntity.setTime(new Timestamp(System.currentTimeMillis()));
        messageEntity.setUsers(user);

        Message messageResponse = messageRepo.save(messageEntity);

        // Perform any necessary message processing (e.g., validation, sanitization)
        message.setMessage(HtmlUtils.htmlEscape(message.getMessage())); // Sanitize message content

        MessageResponse response = new MessageResponse(messageEntity, user);

        // Broadcast the message to the project chat room
        return response;
    }


}
