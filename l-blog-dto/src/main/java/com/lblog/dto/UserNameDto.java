package com.lblog.dto;

import com.lblog.domain.User;

public class UserNameDto {
    private Long id;

    private String name;

    public UserNameDto(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public static UserNameDto getUserName(User user){
        return new UserNameDto(user.getId(), user.getName());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
