package com.restful.api.controller.v1;

import com.restful.api.entity.User;
import com.restful.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SignControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception{
        userRepository.save(User.builder()
                .uid("aaa@naver.com")
                .name("aaa")
                .password(passwordEncoder.encode("1234"))
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
    }

    @Test
    public void 로그인() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "aaa@naver.com");
        params.add("password", "1234");
        mockMvc.perform(post("/v1/signin").params(params))
                .andDo(print()) // 처리 결과 콘솔에 출력
                .andExpect(status().isOk()) // HTTP 상태코드가 200인지 체크
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists())
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    public void 회원가입() throws Exception {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-DD hh:mm"));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "aaa" + date + "@naver.com");
        params.add("password", "1234");
        params.add("name", "aaa" + date);
        mockMvc.perform(post("/v1/signup").params(params))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists());
    }
}


