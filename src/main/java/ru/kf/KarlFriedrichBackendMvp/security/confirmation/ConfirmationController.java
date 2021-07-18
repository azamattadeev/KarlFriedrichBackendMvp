package ru.kf.KarlFriedrichBackendMvp.security.confirmation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kf.KarlFriedrichBackendMvp.security.tokens.AccessRefreshTokenDto;
import ru.kf.KarlFriedrichBackendMvp.security.tokens.AccessRefreshTokenData;

import javax.validation.Valid;

@RestController
public class ConfirmationController {
    private final ConfirmationService confirmationService;

    @Autowired
    public ConfirmationController(ConfirmationService confirmationService) {
        this.confirmationService = confirmationService;
    }

    @PostMapping("/confirm")
    public ResponseEntity<AccessRefreshTokenDto> confirm(@Valid @RequestBody ConfirmationDto confirmationDto) {
        try {
            AccessRefreshTokenData accessRefreshTokenData = confirmationService.confirm(confirmationDto.getEmail(), confirmationDto.getConfirmationCode());
            return ResponseEntity.ok(AccessRefreshTokenDto.createFromAccessRefreshTokenData(accessRefreshTokenData));
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
