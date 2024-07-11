package ru.cft.template.service;
import ru.cft.template.dto.session.CreateSessionRequest;
import ru.cft.template.dto.session.RefreshSessionRequest;
import ru.cft.template.dto.session.SessionDto;
import ru.cft.template.security.SessionUser;

public interface SessionAuthService {
    SessionDto createSession(CreateSessionRequest createSessionRequest);
    SessionDto getCurrentSession(SessionUser sessionUser);

    SessionDto refreshSession(RefreshSessionRequest refreshSessionRequest);
}
