package com.example.StudySpring.controller;

import com.example.StudySpring.entity.User;
import com.example.StudySpring.repository.UserRepository;
import com.example.StudySpring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    @GetMapping("/register")
    public String register() {

        return "account/register";
    }
    @PostMapping("/register")
    public String register(User user) {
        userService.save(user);
        return "redirect:/board/list";
    }

    @GetMapping("/userId")
    public Integer getUserId(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User currentUser = userRepository.findByUsername(username);
        return currentUser.getId();
    }
}
