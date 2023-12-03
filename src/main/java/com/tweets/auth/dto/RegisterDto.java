package com.tweets.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    @NotBlank()
    private String username;

    @NotBlank()
    @Email()
    private String email;

    @NotBlank()
    @URL()
    private String profilePicture;

    @NotBlank()
    private String password;
}