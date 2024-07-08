package ru.cft.template.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.cft.template.dto.user.UserDto;
import ru.cft.template.dto.user.UserRepresentationDto;
import ru.cft.template.mapper.base.BaseMapper;
import ru.cft.template.models.User;

@Mapper(componentModel = "spring", uses = {WalletMapper.class})
public interface UserMapper extends BaseMapper<User, UserDto> {
    @Mapping(target = "walletId", source = "wallet.id")
    UserRepresentationDto toUserRepresentationDTO(User user);
}