package com.restful.api.controller;

import com.restful.api.entity.User;
import com.restful.api.exception.CUserNotFoundException;
import com.restful.api.model.response.CommonResult;
import com.restful.api.model.response.ListResult;
import com.restful.api.model.response.SingleResult;
import com.restful.api.repository.UserRepository;
import com.restful.api.service.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserController {

    private final UserRepository userRepository;
    private final ResponseService responseService;

    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원 조회")
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser() {
        return responseService.getListResult(userRepository.findAll());
    }

    @ApiOperation(value = "회원 단건 조회", notes = "uid로 회원 조회")
    @GetMapping(value = "/user/{uid}")
    public SingleResult<User> findUserById(@ApiParam(value = "회원 uid", required = true) @PathVariable String uid) {
        return responseService.getSingleResult(userRepository.findByUid(uid).orElseThrow(CUserNotFoundException::new));
    }

    @ApiOperation(value = "회원 입력")
    @PostMapping(value = "/user")
    public SingleResult<User> save(@ApiParam(value = "회원 ID", required = true) @RequestParam String uid,
                                   @ApiParam(value = "회원 이름", required = true) @RequestParam String name) {
        User user = User.builder()
                .uid(uid)
                .name(name)
                .build();
        return responseService.getSingleResult(userRepository.save(user));
    }

    @ApiOperation(value = "회원 수정")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
            @ApiParam(value = "회원 번호", required = true) @RequestParam Long msrl,
            @ApiParam(value = "회원 ID", required = true) @RequestParam String uid,
            @ApiParam(value = "회원 이름", required = true) @RequestParam String name) {

        User user = User.builder()
                .msrl(msrl)
                .uid(uid)
                .name(name)
                .build();
        return responseService.getSingleResult(userRepository.save(user));
    }

    @ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제")
    @DeleteMapping(value = "/user/{msrl}")
    public CommonResult delete(
            @ApiParam(value = "회원 번호", required = true) @PathVariable long msrl) {
        userRepository.deleteById(msrl);
        return responseService.getSuccessResult();
    }
}

