package com.restful.api.service.security;

import com.restful.api.exception.CUserNotFoundException;
import com.restful.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String userPk) {
        return (UserDetails)userRepository.findById(Long.valueOf(userPk)).orElseThrow(CUserNotFoundException::new);
    }
}
