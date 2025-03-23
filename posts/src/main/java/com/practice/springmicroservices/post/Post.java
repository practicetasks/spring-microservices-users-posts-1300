package com.practice.springmicroservices.post;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {
    Integer id;              // идентификатор
    String description;      // описание
    Integer authorId;        // автор
    LocalDateTime createdAt; // датаСоздания
}
