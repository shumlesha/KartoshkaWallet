package ru.cft.template.dto.bill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.template.constants.enums.BillStatus;
import ru.cft.template.dto.user.UserRepresentationDTO;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {
    private UUID id;
    private Long cost;
    private UserRepresentationDTO sender;
    private UserRepresentationDTO recipient;
    private String comment;
    private BillStatus billStatus;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
