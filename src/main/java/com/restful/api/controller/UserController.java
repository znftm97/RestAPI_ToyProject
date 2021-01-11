package com.restful.api.controller;

import com.restful.api.entity.User;
import com.restful.api.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserController {

    private final UserRepository userRepository;

    @ApiOperation(value = "회원 조회", notes = "모든 회원 조회")
    @GetMapping("/user")
    public List<User> findAllUser(){
        return userRepository.findAll();
    }

    @ApiOperation(value = "회원가입")
    @PostMapping("/user")
    public User save(@ApiParam(value = "회원 아이디", required = true) @RequestParam String uid,
                     @ApiParam(value = "회원 이름", required = true) @RequestParam String name){
        User user = User.builder()
                .uid(uid)
                .name(name)
                .build();
        return userRepository.save(user);
    }
}
