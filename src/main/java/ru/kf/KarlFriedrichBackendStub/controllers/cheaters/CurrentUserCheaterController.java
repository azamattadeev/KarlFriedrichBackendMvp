package ru.kf.KarlFriedrichBackendStub.controllers.cheaters;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kf.KarlFriedrichBackendStub.entities.User;

@RestController
@RequestMapping("/cheaters/current-user/")
public class CurrentUserCheaterController {

    @GetMapping
    public ResponseEntity<User> getCurrentUser() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentUser == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(currentUser);
    }

}
