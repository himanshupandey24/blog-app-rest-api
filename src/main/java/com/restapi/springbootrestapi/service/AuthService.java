package com.restapi.springbootrestapi.service;

import com.restapi.springbootrestapi.dtos.LoginDto;
import com.restapi.springbootrestapi.dtos.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
