package ru.cft.template.dto.bill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.template.constants.enums.BillStatus;
import ru.cft.template.dto.user.UserRepresentationDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDto {
    private UUID id;
    private Long cost;
    private UserRepresentationDto sender;
    private UserRepresentationDto recipient;
    private String comment;
    private BillStatus billStatus;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
