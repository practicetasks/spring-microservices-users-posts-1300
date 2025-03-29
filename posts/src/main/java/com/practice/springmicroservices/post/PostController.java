package com.practice.springmicroservices.post;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostRepository postRepository;
    private final RestTemplate restTemplate;
    private final String usersServerUrl;

    public PostController(@Value("${users.server.url}") String url,
                          PostRepository postRepository) {
        this.postRepository = postRepository;
        this.usersServerUrl = url;
        this.restTemplate = new RestTemplate();
    }

    @GetMapping("/{id}")
    public Post findById(@PathVariable int id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto create(@RequestBody Post post) {
        String url = usersServerUrl + "/" + post.getAuthorId();
        ///  http://localhost:8080/
        UserDto user = restTemplate.getForObject(url, UserDto.class);

        post.setCreatedAt(LocalDateTime.now());
        postRepository.save(post);

        return PostDto.of(post, user);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleHttpClientError(HttpClientErrorException e) {
        return new ResponseEntity<>(e.getResponseBodyAsByteArray(), e.getStatusCode());
    }
}
