package com.clc.clcchatservice.repo;

import com.clc.clcchatservice.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepo extends JpaRepository<Project, Long> {
}
