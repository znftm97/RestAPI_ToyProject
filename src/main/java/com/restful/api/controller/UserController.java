package com.restful.api.controller;

import com.restful.api.entity.User;
import com.restful.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/user")
    public List<User> findAllUser(){
        return userRepository.findAll();
    }

    @PostMapping("/user")
    public User save(){
        User user = User.builder()
                .uid("aaa@naver.com")
                .name("aaa")
                .build();
        return userRepository.save(user);
    }
}
