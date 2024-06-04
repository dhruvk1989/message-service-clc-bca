package com.clc.clcchatservice.repo;

import com.clc.clcchatservice.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MessageRepo extends JpaRepository<Message, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM MESSAGE where projects_project_id = ?")
    public List<Message> findByProjectId(Long projectId);

}
