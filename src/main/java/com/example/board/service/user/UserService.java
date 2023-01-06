package com.example.board.service.user;

import com.example.board.dto.LoginRequestDto;
import com.example.board.dto.SignupRequestDto;
import com.example.board.dto.UserResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    @Transactional
    void signup(SignupRequestDto signupRequestDto) throws IllegalArgumentException;

    @Transactional(readOnly = true)
    void signin(LoginRequestDto loginRequestDto, HttpServletResponse response);

    @Transactional(readOnly = true)
    List<UserResponseDto> userList();
}
