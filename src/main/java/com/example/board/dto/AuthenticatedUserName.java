package com.example.board.dto;

import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthenticatedUserName {
    private String authUserName;

    public String authUser(Claims claims) {
        return authUserName = claims.getSubject();
    }
}
