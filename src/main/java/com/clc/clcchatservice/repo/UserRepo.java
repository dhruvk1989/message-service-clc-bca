package com.clc.clcchatservice.repo;

import com.clc.clcchatservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {



}
