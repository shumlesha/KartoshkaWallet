package ru.cft.template.mapper;

import org.mapstruct.Mapper;
import ru.cft.template.dto.session.SessionDto;
import ru.cft.template.mapper.base.BaseMapper;
import ru.cft.template.models.Session;

import java.util.Date;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface SessionMapper extends BaseMapper<Session, SessionDto> {
    SessionDto toDTO(Session session, Date expiresAt);
}