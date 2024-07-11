package ru.cft.template.dto.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.template.dto.user.UserDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDto {
    private UUID id;
    private UserDto user;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime expiresAt;
    private boolean enabled;
}
