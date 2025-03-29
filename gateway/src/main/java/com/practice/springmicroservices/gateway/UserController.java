package com.practice.springmicroservices.gateway;

import com.practice.springmicroservices.user.UserDto;
import jakarta.validation.Valid;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final RestTemplate restTemplate;

    public UserController(RestTemplateBuilder builder) {
        this.restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8081"))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable int id) {
        return restTemplate.getForEntity("/users/{id}", Object.class, Map.of("id", id));
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody UserDto userDto) {
        return restTemplate.postForEntity("/users", userDto, Object.class);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleHttpClientError(HttpClientErrorException e) {
        return new ResponseEntity<>(e.getResponseBodyAsByteArray(), e.getStatusCode());
    }
}
