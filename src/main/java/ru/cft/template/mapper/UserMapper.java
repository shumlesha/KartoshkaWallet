package ru.cft.template.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.cft.template.dto.user.UserDto;
import ru.cft.template.dto.user.UserRepresentationDto;
import ru.cft.template.entity.User;

@Mapper(componentModel = "spring", uses = {WalletMapper.class})
public interface UserMapper {
    UserDto toDTO(User user);

    @Mapping(target = "walletId", source = "wallet.id")
    UserRepresentationDto toUserRepresentationDTO(User user);
}