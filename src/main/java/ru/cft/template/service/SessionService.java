package ru.cft.template.service;
import ru.cft.template.models.Session;
import ru.cft.template.models.User;
import ru.cft.template.security.SessionUser;

public interface SessionService {
    void logoutSession(SessionUser sessionUser);

    Session getSessionByAccessToken(String token);

    Session saveSession(String accessToken, String refreshToken, User user);
}