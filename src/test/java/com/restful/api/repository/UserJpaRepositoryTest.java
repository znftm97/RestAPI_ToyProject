package com.restful.api.repository;

import com.restful.api.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserJpaRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void 리포지토리() {
        //given
        String uid = "aaa@naver.com";
        String name = "aaa";

        userRepository.save(User.builder()
                .uid(uid)
                .password(passwordEncoder.encode("1234"))
                .name(name)
                .roles(Collections.singletonList("ROLE+USER"))
                .build());

        //when
        Optional<User> findUser = userRepository.findByUid(uid);

        //then
        assertThat(findUser).isNotNull();
        assertThat(findUser).isNotEmpty();
        assertThat(findUser.get().getName()).isEqualTo(name);
    }
}
