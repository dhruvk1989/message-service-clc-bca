package com.clc.clcchatservice.model;

import com.clc.clcchatservice.entity.Message;
import com.clc.clcchatservice.entity.User;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class MessageResponse {

    private Long messageId;

    private String message;

    private Long projectId;

    private Long userId;

    private UserDto userDto;

    private Timestamp timestamp;

    public MessageResponse(Message message, User user){

        this.messageId = message.getId();
        this.message = message.getMessage();
        this.userId = user.getUser_id();
        this.userDto = new UserDto(user);
        this.timestamp = message.getTime();
        this.projectId = message.getProjects().getProjectId();

    }

}
