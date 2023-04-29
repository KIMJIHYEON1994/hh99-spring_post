package com.sparta.spring_post.security;

import com.sparta.spring_post.entity.Users;
import com.sparta.spring_post.exception.CustomException;
import com.sparta.spring_post.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.sparta.spring_post.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new CustomException(USER_NOT_FOUND);
        }

        return new UserDetailsImpl(user, user.getUsername());
    }

}