package com.example.board.service.user;

import com.example.board.dto.LoginRequestDto;
import com.example.board.dto.SignupRequestDto;
import com.example.board.dto.UserResponseDto;
import com.example.board.entity.User;
import com.example.board.entity.UserRoleEnum;
import com.example.board.jwt.JwtUtil;
import com.example.board.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public void signup(SignupRequestDto signupRequestDto) throws IllegalArgumentException {
        String userName = signupRequestDto.getUserName();
        String userPassword = signupRequestDto.getUserPassword();
        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(userName);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        // 사용자 괄리자 가입
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.getAdminToken().length() > 0) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }
        User user = new User(userName, userPassword, role);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public void signin(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUserName();
        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        System.out.println(user.getRole());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createAccessToken(user.getUsername(), user.getRole()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> userList() {
        return userRepository.findAll().stream().map((x) -> UserResponseDto.of(x)).toList();

    }


}