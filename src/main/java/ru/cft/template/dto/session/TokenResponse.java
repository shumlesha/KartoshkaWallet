package ru.cft.template.dto.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
    private UUID userId;
    private String email;
    private String accessToken;
    private String refreshToken;
}
