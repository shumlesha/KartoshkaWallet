package ru.cft.template.service.impl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.template.models.Session;
import ru.cft.template.models.User;
import ru.cft.template.repository.SessionRepository;
import ru.cft.template.security.SessionUser;
import ru.cft.template.service.SessionService;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;

    @Override
    @Transactional
    public void logoutSession(SessionUser sessionUser) {
        Session session = sessionUser.getSession();
        session.setEnabled(false);

        sessionRepository.save(session);

        SecurityContextHolder.clearContext();
    }

    @Override
    public Session getSessionByAccessToken(String token) {
        return sessionRepository.findByAccessTokenAndEnabledTrue(token).orElse(null);
    }

    @Override
    @Transactional
    public Session saveSession(String accessToken, String refreshToken,  User user) {
        return sessionRepository.save(new Session(accessToken, refreshToken, user, true));
    }

}
