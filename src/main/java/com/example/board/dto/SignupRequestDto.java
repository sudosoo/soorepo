package com.example.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {
    private String userName;
    private String userPassword;
    private boolean admin = false;
    private String adminToken = "";
}


//{
//    "userName" : "asd1",
//    "userPassword" :"asd1",
//    "adminToken":""
//}
