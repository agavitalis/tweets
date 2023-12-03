package com.tweets.auth;

import com.tweets.auth.dto.LoginDto;
import com.tweets.auth.dto.RegisterDto;
import com.tweets.common.exception.TweetsAPIException;
import com.tweets.common.security.JwtTokenProvider;
import com.tweets.user.UserRepository;
import com.tweets.user.dto.UserDto;
import com.tweets.user.entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService{

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private ModelMapper modelMapper;
    @Override
    public UserDto register(RegisterDto registerDto) {

        // check username is already exists in database
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new TweetsAPIException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }

        // check email is already exists in database
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new TweetsAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }

        User user = new User();
        user.setProfilePicture(registerDto.getProfilePicture());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(user);

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }
}
