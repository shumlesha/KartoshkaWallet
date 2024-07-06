package ru.cft.template.service;
import ru.cft.template.dto.user.CreateUserModel;
import ru.cft.template.dto.user.EditUserModel;
import ru.cft.template.models.User;
import ru.cft.template.security.SessionUser;

public interface UserService {
    User createUser(CreateUserModel createUserModel);

    User editUser(EditUserModel editUserModel, SessionUser sessionUser);

    User getUser(SessionUser sessionUser);

    User getByEmail(String email);
}