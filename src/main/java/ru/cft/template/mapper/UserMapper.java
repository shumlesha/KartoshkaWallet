package ru.cft.template.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.cft.template.dto.user.UserDTO;
import ru.cft.template.dto.user.UserRepresentationDTO;
import ru.cft.template.models.User;

@Mapper(componentModel = "spring", uses = {WalletMapper.class})
public interface UserMapper {
    UserDTO toDTO(User user);

    @Mapping(target = "walletId", source = "wallet.id")
    UserRepresentationDTO toUserRepresentationDTO(User user);
}