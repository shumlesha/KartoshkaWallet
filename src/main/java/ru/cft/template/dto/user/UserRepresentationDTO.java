package ru.cft.template.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRepresentationDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private UUID walletId;
}
