package com.moreira.smartstock.tests.factories;

import com.moreira.smartstock.projections.UserDetailsProjection;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsProjectionFactory {

    public static List<UserDetailsProjection> createCustomUser(String username) {
        List<UserDetailsProjection> list = new ArrayList<>();
        list.add(new UserDetailsImp(username, "123456", 1L, "ROLE_OPERATOR"));
        return list;
    }
}

class UserDetailsImp implements UserDetailsProjection {

    private String username;
    private String password;
    private Long roleId;
    private String authority;

    public UserDetailsImp() {
    }

    public UserDetailsImp(String username, String password, Long roleId, String authority) {
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.authority = authority;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Long getRoleId() {
        return roleId;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
