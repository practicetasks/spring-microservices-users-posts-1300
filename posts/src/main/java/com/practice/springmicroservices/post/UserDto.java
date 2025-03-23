package com.practice.springmicroservices.post;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    int id;
    String login;
    LocalDateTime createdAt;
}
