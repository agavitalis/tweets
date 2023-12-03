package com.tweets.user;

import com.tweets.common.response.CustomResponse;
import com.tweets.user.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<CustomResponse> getUserById(@PathVariable("id") Long userId){
        UserDto userDto = userService.getUserById(userId);
        CustomResponse response = new CustomResponse(HttpStatus.OK.toString(), "User Successfully retrieved", userDto);

        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<CustomResponse> getAllUsers(){
        List<UserDto> users = userService.getAllUsers();
        CustomResponse response = new CustomResponse(HttpStatus.OK.toString(), "Users Successfully retrieved", users);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomResponse> updateUser(@PathVariable("id") Long userId,
                                                          @RequestBody UserDto updatedUser){
        UserDto userDto = userService.updateUser(userId, updatedUser);
        CustomResponse response = new CustomResponse(HttpStatus.OK.toString(), "Users Successfully updated", userDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CustomResponse> deleteUser(@PathVariable("id") Long userId){
        userService.deleteUser(userId);
        CustomResponse response = new CustomResponse(HttpStatus.OK.toString(), "Users Successfully deleted", null);
        return ResponseEntity.ok(response);
    }

}
