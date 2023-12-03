package com.tweets.user;

import com.tweets.common.exception.TweetsAPIException;
import com.tweets.user.dto.UserDto;
import com.tweets.user.entity.User;
import com.tweets.user.entity.UserFollows;
import com.tweets.user.mapper.UserMapper;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements IUserService{

    private UserRepository userRepository;
    private UserFollowsRepository userFollowsRepository;
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
    @Transactional
    public void followUser(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId).orElseThrow(
                () -> new TweetsAPIException(HttpStatus.NOT_FOUND,"User with the given id: " + followerId + " does not exist")
        );

        User following = userRepository.findById(followingId).orElseThrow(
                () -> new TweetsAPIException(HttpStatus.NOT_FOUND,"User with the given id: " + followingId + " does not exist")
        );

        if (!follower.getFollowing().contains(following)) {
            follower.follow(following);
        }
    }

    @Transactional
    public void unFollowUser(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId).orElseThrow(
                () -> new TweetsAPIException(HttpStatus.NOT_FOUND,"User with the given id: " + followerId + " does not exist")
        );

        User following = userRepository.findById(followingId).orElseThrow(
                () -> new TweetsAPIException(HttpStatus.NOT_FOUND,"User with the given id: " + followingId + " does not exist")
        );

        UserFollows userFollows = userFollowsRepository.findByFollowerAndFollowing(follower, following);

        if (userFollows != null) {
            // Remove the association
            follower.unfollow(following);

            // Delete the UserFollows entity
            userFollowsRepository.deleteById(userFollows.getId());
        }

    }

    public Set<UserFollows> getFollowers(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () ->  new TweetsAPIException(HttpStatus.NOT_FOUND, "User with the given id: " + userId + " does not exist")
        );
        return user.getFollowers();
    }
}
