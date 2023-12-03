package com.tweets.user;

import com.tweets.user.dto.UserDto;

import java.util.List;

public interface IUserService {

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    UserDto updateUser(Long userId, UserDto updatedUser);

    void deleteUser(Long userId);
}
