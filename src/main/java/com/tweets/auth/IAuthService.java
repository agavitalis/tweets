package com.tweets.auth;

import com.tweets.auth.dto.LoginDto;
import com.tweets.auth.dto.RegisterDto;
import com.tweets.user.dto.UserDto;

public interface IAuthService {
    UserDto register(RegisterDto registerDto);
    String login(LoginDto loginDto);
}
