package ru.cft.template.dto.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.template.dto.user.UserDTO;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {
    private UUID id;
    private UserDTO user;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime expiresAt;
    private boolean enabled;
}
