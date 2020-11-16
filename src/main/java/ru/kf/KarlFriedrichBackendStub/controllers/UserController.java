package ru.kf.KarlFriedrichBackendStub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kf.KarlFriedrichBackendStub.entities.User;
import ru.kf.KarlFriedrichBackendStub.repositories.UserRepository;

import java.util.List;

@RestController
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> getUsers() {
        List<User> userList = userRepository.findAll();
        return ResponseEntity.ok(userList);
    }

}
