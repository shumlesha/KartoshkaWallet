package ru.cft.template.mapper;

import org.mapstruct.Mapper;
import ru.cft.template.dto.session.SessionDto;
import ru.cft.template.entity.Session;

import java.util.Date;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface SessionMapper {
    SessionDto toDTO(Session session, Date expiresAt);
}