package com.practice.springmicroservices.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final List<Post> posts = new ArrayList<>();
    private int nextId = 1;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String usersServerUrl = "http://localhost:8081/users";

    @GetMapping("/{id}")
    public Post findById(@PathVariable int id) {
        return posts.stream()
                .filter(post -> post.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto create(@RequestBody Post post) {
        String url = usersServerUrl + "/" + post.getAuthorId();
        UserDto user =  restTemplate.getForObject(url, UserDto.class);

        post.setId(nextId++);
        post.setCreatedAt(LocalDateTime.now());
        posts.add(post);
        return PostDto.of(post, user);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleHttpClientError(HttpClientErrorException e) {
        return new ResponseEntity<>(e.getResponseBodyAsByteArray(), e.getStatusCode());
    }
}
