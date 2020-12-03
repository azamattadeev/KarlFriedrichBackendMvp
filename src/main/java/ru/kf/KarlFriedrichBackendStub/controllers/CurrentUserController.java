package ru.kf.KarlFriedrichBackendStub.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kf.KarlFriedrichBackendStub.dto.outgoing.CurrentUserDto;
import ru.kf.KarlFriedrichBackendStub.entities.User;

@RestController
public class CurrentUserController {

    @GetMapping("/current-user")
    public ResponseEntity<CurrentUserDto> getCurrentUser() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentUser == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(CurrentUserDto.createFromUser(currentUser));
    }

}
