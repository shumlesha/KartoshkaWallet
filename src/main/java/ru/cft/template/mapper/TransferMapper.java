package ru.cft.template.mapper;
import org.mapstruct.Mapper;
import ru.cft.template.dto.transfer.TransferDto;
import ru.cft.template.mapper.base.BaseMapper;
import ru.cft.template.models.Transfer;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TransferMapper extends BaseMapper<Transfer, TransferDto> { }