package com.practice.springmicroservices.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody UserDto userDto) {
        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
