package com.example.board.controller;

import com.example.board.dto.LoginRequestDto;
import com.example.board.dto.SignupRequestDto;
import com.example.board.dto.UserResponseDto;
import com.example.board.service.user.UserServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return "success";
    }

    @ResponseBody
    @PostMapping("/signin")
    public void signin(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.signin(loginRequestDto, response);
    }

    @GetMapping("/users")
    public List<UserResponseDto> userList() {
        return userService.userList();
    }

}