package com.practice.springmicroservices.post;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDto {
    int id;
    String description;
    UserDto author;
    LocalDateTime createdAt;

    public static PostDto of(Post post, UserDto author) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setDescription(post.getDescription());
        postDto.setAuthor(author);
        postDto.setCreatedAt(post.getCreatedAt());
        return postDto;
    }
}
