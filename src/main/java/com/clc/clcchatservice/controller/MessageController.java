package com.clc.clcchatservice.controller;

import com.clc.clcchatservice.entity.User;
import com.clc.clcchatservice.model.MessageResponse;
import com.clc.clcchatservice.service.AuthHandler;
import com.clc.clcchatservice.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private AuthHandler authHandler;

    @Autowired
    private MessageService messageService;

    @GetMapping("/{projectId}")
    public ResponseEntity getMessages(HttpServletRequest httpServletRequest, @PathVariable String projectId){

        String authorization = httpServletRequest.getHeader("Authorization");
        boolean authorized = false;

        User user = null;

        if(authorization != null && authorization.startsWith("Bearer ")) {
            try {
                List response = authHandler.authHandler(authorization.substring(7));
                authorized = (boolean) response.get(0);
                user = (User) response.get(1);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }catch (Exception e){
                e.printStackTrace();
                if(e.getMessage().startsWith("401")){
                    return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
                }else{
                    return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }

        if(!authorized){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        try {
            List<MessageResponse> messages = messageService.getMessages(Long.parseLong(projectId));
            return new ResponseEntity(messages, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
