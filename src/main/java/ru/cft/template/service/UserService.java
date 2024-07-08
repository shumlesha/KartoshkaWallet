package ru.cft.template.service;
import ru.cft.template.dto.user.CreateUserRequest;
import ru.cft.template.dto.user.EditUserRequest;
import ru.cft.template.models.User;
import ru.cft.template.security.SessionUser;

public interface UserService {
    User createUser(CreateUserRequest createUserRequest);

    User editUser(EditUserRequest editUserRequest, SessionUser sessionUser);

    User getUser(SessionUser sessionUser);

    User getByEmail(String email);
}