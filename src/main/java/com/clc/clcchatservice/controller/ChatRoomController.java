package com.clc.clcchatservice.controller;

import com.clc.clcchatservice.entity.User;
import com.clc.clcchatservice.service.AuthHandler;
import com.clc.clcchatservice.service.ChatRoomService;
import com.netflix.discovery.converters.Auto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RequestMapping("/chatRoom")
@RestController
public class ChatRoomController {

    @Autowired
    private AuthHandler authHandler;

    @Autowired
    private ChatRoomService chatRoomService;

    @GetMapping("")
    public String ok(){
        return "ok";
    }

    @GetMapping("/joinRoom")
    public ResponseEntity joinRoom(HttpServletRequest httpServletRequest, @RequestParam Long projectId){

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
            chatRoomService.joinRoom(user, projectId);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/exitRoom")
    public ResponseEntity exitRoom(HttpServletRequest httpServletRequest, @RequestParam Long projectId){

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
            chatRoomService.exitRoom(user, projectId);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
