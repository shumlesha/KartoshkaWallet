package ru.cft.template.mapper;
import org.mapstruct.*;
import ru.cft.template.dto.user.CreateUserRequest;
import ru.cft.template.dto.user.EditUserRequest;
import ru.cft.template.dto.user.UserDto;
import ru.cft.template.dto.user.UserRepresentationDto;
import ru.cft.template.entity.User;

@Mapper(componentModel = "spring", uses = {WalletMapper.class})
public interface UserMapper {
    UserDto toDTO(User user);

    @Mapping(target = "walletId", source = "wallet.id")
    UserRepresentationDto toUserRepresentationDTO(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void update(@MappingTarget User user, EditUserRequest editUserRequest);

    @Mapping(target = "password", ignore = true)
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    User toEntity(CreateUserRequest createUserRequest);
}