package ru.cft.template.mapper;
import org.mapstruct.Mapper;
import ru.cft.template.dto.transfer.TransferDto;
import ru.cft.template.entity.Transfer;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TransferMapper {
    TransferDto toDTO(Transfer transfer);
}