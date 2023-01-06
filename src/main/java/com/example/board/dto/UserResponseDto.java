package com.example.board.dto;

import com.example.board.entity.User;
import com.example.board.entity.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String userName;
    private UserRoleEnum roleEnum;


    public static UserResponseDto of(User user) {
        return new UserResponseDto(user.getUsername(), user.getRole());
    }

}