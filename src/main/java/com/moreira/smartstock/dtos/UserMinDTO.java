package com.moreira.smartstock.dtos;

import com.moreira.smartstock.entities.User;

public record UserMinDTO(Long id, String name) {

    public UserMinDTO(User user) {
        this(user.getId(), user.getName());
    }
}
