package ru.cft.template.dto.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.template.constants.enums.TransferStatus;
import ru.cft.template.dto.user.UserRepresentationDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {
    private UUID id;
    private Long cost;
    private UserRepresentationDto sender;
    private UserRepresentationDto recipient;
    private String comment;
    private TransferStatus transferStatus;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
