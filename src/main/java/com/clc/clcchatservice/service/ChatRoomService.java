package com.clc.clcchatservice.service;

import com.clc.clcchatservice.entity.Project;
import com.clc.clcchatservice.entity.User;
import com.clc.clcchatservice.repo.ProjectRepo;
import com.clc.clcchatservice.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UserRepo userRepo;

    public void joinRoom(User user, Long projectId){

        User user1 = userRepo.findById(user.getUser_id()).get();
        Project project = projectRepo.findById(projectId).get();
        project.getUserSet().add(user1);

        projectRepo.save(project);

        user1.getProjectSet().add(project);

        userRepo.save(user1);

    }

    public void exitRoom(User user, Long projectId){

        User user1 = userRepo.findById(user.getUser_id()).get();
        Project project = projectRepo.findById(projectId).get();
        project.getUserSet().remove(user1);

        projectRepo.save(project);

        user1.getProjectSet().remove(project);

        userRepo.save(user1);

    }

}
