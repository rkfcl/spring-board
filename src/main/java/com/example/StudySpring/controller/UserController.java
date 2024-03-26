package com.example.StudySpring.controller;

import com.example.StudySpring.entity.User;
import com.example.StudySpring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserRepository userRepository;


    @GetMapping("/users")
    List<User> userList() {
        return userRepository.findAll();
    }

}
