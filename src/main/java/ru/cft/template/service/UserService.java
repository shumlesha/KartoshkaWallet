package ru.cft.template.service;
import ru.cft.template.dto.user.CreateUserRequest;
import ru.cft.template.dto.user.EditUserRequest;
import ru.cft.template.dto.user.UserDto;
import ru.cft.template.models.User;
import ru.cft.template.security.SessionUser;

public interface UserService {
    UserDto createUser(CreateUserRequest createUserRequest);

    UserDto editUser(EditUserRequest editUserRequest, SessionUser sessionUser);

    UserDto getUser(SessionUser sessionUser);

    User getByEmail(String email);
}