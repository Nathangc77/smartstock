package com.moreira.smartstock.tests.factories;

import com.moreira.smartstock.entities.User;

public class UserFactory {

    public static User createCustomUser(String username) {
        return new User(1L, "", username, "123456");
    }
}
