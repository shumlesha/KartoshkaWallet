package ru.cft.template.service;
import ru.cft.template.entity.Session;
import ru.cft.template.entity.User;
import ru.cft.template.security.SessionUser;

public interface SessionService {
    void logoutSession(SessionUser sessionUser);

    Session getSessionByAccessToken(String token);

    Session saveSession(String accessToken, String refreshToken, User user);
}