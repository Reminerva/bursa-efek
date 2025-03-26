package com.bursaefek.bursaefek.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.bursaefek.bursaefek.constant.ERole;
import com.bursaefek.bursaefek.entity.AppUser;
import com.bursaefek.bursaefek.model.request.UserRequest;
import com.bursaefek.bursaefek.model.response.LoginResponse;
import com.bursaefek.bursaefek.model.response.LogoutResponse;
import com.bursaefek.bursaefek.model.response.RegisterResponse;
import com.bursaefek.bursaefek.repository.AppUserRepository;
import com.bursaefek.bursaefek.security.JwtAuthenticationFilter;
import com.bursaefek.bursaefek.security.JwtTokenProvider;
import com.bursaefek.bursaefek.service.AppUserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTokenBlackListService redisTokenBlackListService;
    private final AuthenticationManager authenticationManager;

    @Override
    public RegisterResponse register(UserRequest userRequest) {
        try {
            System.out.println(userRequest.getEmail());
            AppUser user = AppUser.builder()
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role(ERole.ROLE_ADMIN)
                .build();

            userRepository.saveAndFlush(user);

            RegisterResponse response = RegisterResponse.builder()
                    .email(user.getEmail())
                    .role(user.getRole().name())
                    .build();

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public AppUser getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public LoginResponse login(UserRequest userRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword())
            );

            User user = (User) authentication.getPrincipal();

            String token = jwtTokenProvider.generateToken(user.getUsername(), user.getAuthorities().toString());

            LoginResponse response = LoginResponse.builder()
                .email(user.getUsername())
                .token(token)
                .build();
    
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public LogoutResponse logout(HttpServletRequest logoutRequest) {
        String token =  jwtAuthenticationFilter.extractTokenFromRequest(logoutRequest);
        
        if (token == null || !jwtTokenProvider.validateToken(token)) {

            throw new RuntimeException("Token is null");
        }

        Long expirationTime = jwtTokenProvider.getExpirationTime(token);
        redisTokenBlackListService.blackListToken(token, expirationTime);

        LogoutResponse response = LogoutResponse.builder()
            .statusMessage("Logout successful")
            .accessToken(token)
            .build();

        return response;
    }
}
