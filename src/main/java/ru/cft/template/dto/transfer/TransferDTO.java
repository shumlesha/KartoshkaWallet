package ru.cft.template.dto.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.template.constants.enums.TransferStatus;
import ru.cft.template.dto.user.UserRepresentationDTO;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDTO {
    private UUID id;
    private Long cost;
    private UserRepresentationDTO sender;
    private UserRepresentationDTO recipient;
    private String comment;
    private TransferStatus transferStatus;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
