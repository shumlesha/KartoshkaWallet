package ru.cft.template.mapper;
import org.mapstruct.Mapper;
import ru.cft.template.dto.bill.BillDTO;
import ru.cft.template.models.Bill;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface BillMapper {
    BillDTO toDTO(Bill bill);
}