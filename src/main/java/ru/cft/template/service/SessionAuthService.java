package ru.cft.template.service;
import ru.cft.template.dto.session.CreateSessionModel;
import ru.cft.template.dto.session.RefreshSessionModel;
import ru.cft.template.dto.session.SessionDTO;
import ru.cft.template.security.SessionUser;

public interface SessionAuthService {
    SessionDTO createSession(CreateSessionModel createSessionModel);
    SessionDTO getCurrentSession(SessionUser sessionUser);

    SessionDTO refreshSession(RefreshSessionModel refreshSessionModel);
}
