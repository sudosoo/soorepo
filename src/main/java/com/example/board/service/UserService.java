package com.example.board.service;

import com.example.board.dto.LoginRequestDto;
import com.example.board.dto.SignupRequestDto;
import com.example.board.dto.UserResponseDto;
import com.example.board.entity.User;
import com.example.board.entity.UserRoleEnum;
import com.example.board.jwt.JwtUtil;
import com.example.board.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public void signup(SignupRequestDto signupRequestDto) throws IllegalArgumentException {
        String userName = signupRequestDto.getUserName();
        String userPassword = signupRequestDto.getUserPassword();


        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(userName);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        System.out.println(signupRequestDto.getAdminToken().length());
        if (signupRequestDto.getAdminToken().length() > 0){
            if(!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)){
                    throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
                role = UserRoleEnum.ADMIN;
        }
        User user = new User(userName, userPassword, role);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUserName();
        String userpassword = loginRequestDto.getUserPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if(!user.getPassword().equals(userpassword)){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> users(HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(jwtUtil.validateToken(token)){
            claims = jwtUtil.getUserInfoFromToken(token);
        }else {
            throw new IllegalArgumentException("Token Error");
        }

        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        UserRoleEnum userRoleEnum = user.getRole();
        Collection<User> userCheck;

        if(userRoleEnum == UserRoleEnum.USER){
            userCheck = userRepository.findAllByUsername(claims.getSubject());
        }else{
            userCheck = userRepository.findAll();
        }
        return userCheck.stream().map(UserResponseDto::of).toList();

    }


}