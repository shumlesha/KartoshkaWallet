package ru.cft.template.security;

import lombok.experimental.UtilityClass;
import ru.cft.template.models.Session;
import ru.cft.template.models.User;

@UtilityClass
public class SessionUserFactory {

    public static SessionUser create(User user, Session session) {
        return new SessionUser(
                user.getId(),
                session,
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPatronymic(),
                user.getPassword());
    }

    public static SessionUser create(User user) {
        return new SessionUser(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPatronymic(),
                user.getPassword());
    }
}
