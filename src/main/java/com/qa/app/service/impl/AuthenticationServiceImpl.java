package com.qa.app.service.impl;

import com.qa.app.dao.request.SignUpRequest;
import com.qa.app.dao.request.SigninRequest;
import com.qa.app.dao.response.JwtAuthenticationResponse;
import com.qa.app.dao.response.UserDao;
import com.qa.app.entities.Role;
import com.qa.app.entities.User;
import com.qa.app.repository.UserRepository;
import com.qa.app.service.AuthenticationService;
import com.qa.app.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;


    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User already registered.");
        }

        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .bug(0L).coin(0L).victory(0L).refill(0L)
                .role(Role.USER).build();
        User newUser = userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        log.info("USER REGISTERED: " + newUser);
        return JwtAuthenticationResponse.builder()
                .user(modelMapper.map(newUser, UserDao.class)).token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        Authentication a = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        var userDao = modelMapper.map(user, UserDao.class);
        log.info("USER FOUND: " + userDao);
        return JwtAuthenticationResponse.builder()
                .token(jwt)
                .user(userDao)
                .build();
    }

}
