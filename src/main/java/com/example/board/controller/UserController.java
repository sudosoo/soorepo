package com.example.board.controller;

import com.example.board.checkUtil.CheckUtil;
import com.example.board.dto.LoginRequestDto;
import com.example.board.dto.SignupRequestDto;
import com.example.board.dto.UserResponseDto;
import com.example.board.jwt.JwtUtil;
import com.example.board.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final CheckUtil checkUtil;

    @PostMapping("/user/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return "success";
    }

    @ResponseBody
    @PostMapping("/user/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(userService.login(loginRequestDto).getUserName(), userService.login(loginRequestDto).getRoleEnum()));
        return "success";
    }

    //로그인할때 토큰체크 한번하고 인증완료된 유저로 변경 < 인증완료된 유저와 유저권한 체크

    @GetMapping("/user/users")
    public List<UserResponseDto> userList(HttpServletRequest request) {
        Claims claims = checkUtil.tokenCheck(request);
        return userService.userList(claims);
    }

}