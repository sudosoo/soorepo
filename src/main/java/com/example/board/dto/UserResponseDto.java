package com.example.board.dto;

import com.example.board.entity.User;
import com.example.board.entity.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String userName;
    private boolean authenticatedUser = false;
    private UserRoleEnum roleEnum;

    public UserResponseDto(User user) {
        this.userName = user.getUsername();
        this.roleEnum = user.getRole();

    }

    public UserResponseDto(String username, UserRoleEnum role) {
        this.userName = username;
        this.roleEnum = role;
    }

    public static UserResponseDto of(User user) {
        return new UserResponseDto(user.getUsername(), user.getRole());
    }

}