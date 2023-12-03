package com.tweets.user;

import com.tweets.common.exception.ResourceNotFoundException;
import com.tweets.common.exception.TweetsAPIException;
import com.tweets.user.dto.UserDto;
import com.tweets.user.entity.User;
import com.tweets.user.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements IUserService{

    private UserRepository userRepository;
    private ModelMapper modelMapper;

//    @Override
//    public UserDto createUser(UserDto userDto) {
//        User user = modelMapper.map(userDto, User.class);
//        User savedUser = userRepository.save(user);
//        return modelMapper.map(savedUser, UserDto.class);
//    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () ->  new TweetsAPIException(HttpStatus.NOT_FOUND, "User with the given id: " + userId + " does not exist")
        );
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto getUserByEmailORUsername(String email, String userName) {
        User user = userRepository.findByUsernameOrEmail(email, userName).orElseThrow(
                () ->  new TweetsAPIException(HttpStatus.NOT_FOUND, "User with the given credentials does not exist")
        );
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long userId, UserDto updatedUser) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new TweetsAPIException(HttpStatus.NOT_FOUND,"User with the given id: " + userId + " does not exist")
        );

        user.setEmail(updatedUser.getEmail());
        user.setUsername(updatedUser.getUsername());
        user.setProfilePicture(updatedUser.getProfilePicture());

        User savedUser = userRepository.save(user);

        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).orElseThrow(
                () -> new TweetsAPIException(HttpStatus.NOT_FOUND,"User with the given id: " + userId + " does not exist")
        );

        userRepository.deleteById(userId);
    }
}
