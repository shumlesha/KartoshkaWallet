package ru.cft.template.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.cft.template.dto.session.SessionDTO;
import ru.cft.template.dto.session.TokenResponse;
import ru.cft.template.models.Session;

import java.util.Date;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface SessionMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "email", source = "user.email")
    TokenResponse toTokenResponse(Session session);

    @Mapping(target = "expiresAt", source = "expiresAt")
    SessionDTO toDTO(Session session, Date expiresAt);
}