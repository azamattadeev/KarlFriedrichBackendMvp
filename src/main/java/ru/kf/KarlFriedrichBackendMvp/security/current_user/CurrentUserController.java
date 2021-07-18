package ru.kf.KarlFriedrichBackendMvp.security.current_user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kf.KarlFriedrichBackendMvp.security.user.User;

@RestController
public class CurrentUserController {

    @GetMapping("/current-user")
    public ResponseEntity<CurrentUserDto> getCurrentUser() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentUser == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(CurrentUserDto.createFromUser(currentUser));
    }

}
