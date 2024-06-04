package com.clc.clcchatservice.service;

import com.clc.clcchatservice.entity.User;
import com.clc.clcchatservice.model.MessageResponse;
import com.clc.clcchatservice.repo.MessageRepo;
import com.clc.clcchatservice.repo.ProjectRepo;
import com.clc.clcchatservice.repo.UserRepo;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UserRepo userRepo;

    public List<MessageResponse> getMessages(Long projectId){

        List<MessageResponse> collect = messageRepo.findByProjectId(projectId).stream().map(a -> {
            return new MessageResponse(a, a.getUsers());
        }).collect(Collectors.toList());

        return collect;

    }

}
