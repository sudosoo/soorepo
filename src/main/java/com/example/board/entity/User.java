package com.example.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "users")
@NoArgsConstructor
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Pattern(regexp = "^[a-z0-9]{4,10}", message = "아이디는 영문자와 숫자 적어도 1개 이상 포함된 4자~10자의 아이디여야 합니다.")
    @Column(nullable = false, unique = true)
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}", message = "비밀번호는 영문자와 숫자 적어도 1개 이상 포함된 8자~15자의 비밀번호여야 합니다.")
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;


    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }


    public void toString(Object object) {
        System.out.println(object);
    }


}