package ru.kf.KarlFriedrichBackendStub.controllers.cheaters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kf.KarlFriedrichBackendStub.entities.User;
import ru.kf.KarlFriedrichBackendStub.repositories.UserRepository;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cheaters/user/")
public class UserCheaterController {
    private final UserRepository userRepository;

    @Autowired
    public UserCheaterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> userList = userRepository.findAll();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        return (user != null)
                ? ResponseEntity.ok(user)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody User user) {
        if (userRepository.findUserByEmail(user.getEmail()) != null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        user = userRepository.save(user);
        return ResponseEntity.created(URI.create("/cheaters/user/" + user.getId())).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
