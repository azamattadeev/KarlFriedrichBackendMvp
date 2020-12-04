package ru.kf.KarlFriedrichBackendStub.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kf.KarlFriedrichBackendStub.entities.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserDto {
    private Long id;
    private String email;
    private String name;
    private String language;

    public static CurrentUserDto createFromUser(User user) {
        return new CurrentUserDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getLanguage()
        );
    }

}
