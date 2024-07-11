package ru.cft.template.mapper;
import org.mapstruct.Mapper;
import ru.cft.template.dto.bill.BillDto;
import ru.cft.template.entity.Bill;


@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface BillMapper {
    BillDto toDTO(Bill bill);
}