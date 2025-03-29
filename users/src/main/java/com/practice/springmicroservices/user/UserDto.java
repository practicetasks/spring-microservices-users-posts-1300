package com.practice.springmicroservices.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
    @NotBlank(message = "Логин пользователя не может быть пустым")
    private String login;
}
