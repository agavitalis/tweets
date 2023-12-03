package com.tweets.auth;

import com.tweets.common.response.JwtAuthResponse;
import com.tweets.auth.dto.LoginDto;
import com.tweets.auth.dto.RegisterDto;
import com.tweets.common.response.CustomResponse;
import com.tweets.user.UserService;
import com.tweets.user.dto.UserDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;
    private UserService userService;

    // Build Register REST API
    @PostMapping("/register")
    public ResponseEntity<CustomResponse> register(@Valid @RequestBody RegisterDto registerDto) {
        UserDto user = authService.register(registerDto);
        CustomResponse response = new CustomResponse(HttpStatus.CREATED.toString(), "User Successfully created", user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<CustomResponse> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);

        UserDto userDto = userService.getUserByEmailORUsername(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setUser(userDto);

        CustomResponse response = new CustomResponse(HttpStatus.OK.toString(), "User Successfully logged in", jwtAuthResponse);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
