package com.bursaefek.bursaefek.service;

import com.bursaefek.bursaefek.model.request.UserRequest;
import com.bursaefek.bursaefek.model.response.LoginResponse;
import com.bursaefek.bursaefek.model.response.LogoutResponse;
import com.bursaefek.bursaefek.model.response.RegisterResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface AppUserService {

    RegisterResponse register (UserRequest userRequest);
    LoginResponse login (UserRequest userRequest);
    LogoutResponse logout (HttpServletRequest logoutRequest);

}