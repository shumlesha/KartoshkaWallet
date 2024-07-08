package ru.cft.template.mapper;
import org.mapstruct.Mapper;
import ru.cft.template.dto.bill.BillDto;
import ru.cft.template.mapper.base.BaseMapper;
import ru.cft.template.models.Bill;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface BillMapper extends BaseMapper<Bill, BillDto> { }