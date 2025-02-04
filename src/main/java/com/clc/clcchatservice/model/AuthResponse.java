package com.clc.clcchatservice.model;

import com.clc.clcchatservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private boolean expired;
    private boolean invalid;
    private boolean userFound;
    private User user;

}
