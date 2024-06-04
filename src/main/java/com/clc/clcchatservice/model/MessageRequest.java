package com.clc.clcchatservice.model;

import com.clc.clcchatservice.entity.Message;
import com.clc.clcchatservice.entity.User;
import lombok.Data;

@Data
public class MessageRequest {

    private String message;

    private Long userId;

    private String jwt;

}
