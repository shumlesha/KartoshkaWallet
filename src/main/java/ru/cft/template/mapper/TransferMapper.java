package ru.cft.template.mapper;
import org.mapstruct.Mapper;
import ru.cft.template.dto.transfer.TransferDTO;
import ru.cft.template.models.Transfer;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TransferMapper {
    TransferDTO toDTO(Transfer transfer);
}