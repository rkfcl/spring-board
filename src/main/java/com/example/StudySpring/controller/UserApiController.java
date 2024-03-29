package com.example.StudySpring.controller;

import com.example.StudySpring.entity.Board;
import com.example.StudySpring.entity.User;
import com.example.StudySpring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserApiController {
    private final UserRepository userRepository;

    @GetMapping("/users")
    List<User> userList() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable("id") Integer id){
        return userRepository.findById(id).orElse(null);
    }
    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser,@PathVariable("id") Integer id){
        return userRepository.findById(id)
                .map(user -> {
//                    user.setBoards(newUser.getBoards());
                    user.getBoards().clear();
                    user.getBoards().addAll(newUser.getBoards());
                    for (Board board : user.getBoards()){
                        board.setUser(user);
                    }
                    return userRepository.save(user);
                })
                .orElseGet(()->{
                   newUser.setId(id);
                   return userRepository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable("id") Integer id){
        userRepository.deleteById(id);
    }
}
