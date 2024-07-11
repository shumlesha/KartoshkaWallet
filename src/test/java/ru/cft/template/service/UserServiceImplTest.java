package ru.cft.template.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.cft.template.dto.user.CreateUserRequest;
import ru.cft.template.dto.user.EditUserRequest;
import ru.cft.template.dto.user.UserDto;
import ru.cft.template.entity.Session;
import ru.cft.template.entity.User;
import ru.cft.template.exception.user.UserAlreadyExistsException;
import ru.cft.template.exception.user.UserNotFoundException;
import ru.cft.template.mapper.UserMapper;
import ru.cft.template.repository.UserRepository;
import ru.cft.template.security.SessionUser;
import ru.cft.template.service.impl.UserServiceImpl;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("createUser - SUCCESS")
    void test_createUser_Success() throws Exception {
        // Given
        CreateUserRequest createUserRequest = new CreateUserRequest(
                "Aleksey",
                "Shumkov",
                "Sergeevich",
                "+79061982546",
                "shumlesha@gmail.com",
                LocalDate.of(2005, 1, 21),
                "Passsword?3241"
        );

        // When
        when(userRepository.existsByEmail(createUserRequest.getEmail())).thenReturn(false);
        when(userRepository.existsByPhoneNumber(createUserRequest.getPhoneNumber())).thenReturn(false);
        when(passwordEncoder.encode(createUserRequest.getPassword())).thenReturn("encoded");
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(new User());
        when(userMapper.toDTO(any(User.class))).thenReturn(new UserDto());

        UserDto userDto = userService.createUser(createUserRequest);

        // Then
        assertNotNull(userDto);
        verify(userRepository).existsByEmail(createUserRequest.getEmail());
        verify(userRepository).existsByPhoneNumber(createUserRequest.getPhoneNumber());
        verify(passwordEncoder).encode(createUserRequest.getPassword());
        verify(userRepository).saveAndFlush(any(User.class));
        verify(userMapper).toDTO(any(User.class));
    }

    @Test
    @DisplayName("createUser - FAIL (Email Already Exists)")
    void test_createUser_EmailAlreadyExists() {
        // Given
        CreateUserRequest createUserRequest = new CreateUserRequest(
                "Aleksey",
                "Shumkov",
                "Sergeevich",
                "+79061982546",
                "shumlesha@gmail.com",
                LocalDate.of(2005, 1, 21),
                "Passsword?3241"
        );

        // When
        when(userRepository.existsByEmail(createUserRequest.getEmail())).thenReturn(true);


        // Then
        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(createUserRequest));
        verify(userRepository).existsByEmail(createUserRequest.getEmail());
        verify(userRepository, never()).saveAndFlush(any(User.class));
    }

    @Test
    @DisplayName("createUser - FAIL (Phonenumber Already Exists)")
    void test_creatUser_PhoneNumberAlreadyExists() {
        // Given
        CreateUserRequest createUserRequest = new CreateUserRequest(
                "Aleksey",
                "Shumkov",
                "Sergeevich",
                "+79061982546",
                "shumlesha@gmail.com",
                LocalDate.of(2005, 1, 21),
                "Passsword?3241"
        );

        // When
        when(userRepository.existsByEmail(createUserRequest.getEmail())).thenReturn(false);
        when(userRepository.existsByPhoneNumber(createUserRequest.getPhoneNumber())).thenReturn(true);

        // Then
        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(createUserRequest));
        verify(userRepository).existsByEmail(createUserRequest.getEmail());
        verify(userRepository).existsByPhoneNumber(createUserRequest.getPhoneNumber());
        verify(userRepository, never()).saveAndFlush(any(User.class));
    }


    @Test
    @DisplayName("editUser - SUCCESS")
    void test_editUser_Success() throws Exception {
        // Given
        EditUserRequest editUserRequest = new EditUserRequest(
                "Алексей",
                "Шумков",
                "Сергеевич",
                null
        );

        User user = new User();
        user.setFirstName("Alexey");
        user.setLastName("Shumkov");
        user.setPatronymic("Sergeevich");

        Session session = new Session();
        session.setUser(user);

        SessionUser sessionUser = new SessionUser(
                UUID.randomUUID(),
                session,
                "email",
                user.getFirstName(),
                user.getLastName(),
                user.getPatronymic(),
                "Alek?2005"
        );

        // When
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(user);
        when(userMapper.toDTO(any(User.class))).thenReturn(new UserDto());

        // Then
        UserDto userDto = userService.editUser(editUserRequest, sessionUser);

        assertNotNull(userDto);
        assertEquals(editUserRequest.getFirstName(), user.getFirstName());
        assertEquals(editUserRequest.getLastName(), user.getLastName());
        assertEquals(editUserRequest.getPatronymic(), user.getPatronymic());
        verify(userRepository).saveAndFlush(user);
        verify(userMapper).toDTO(user);
    }

    @Test
    @DisplayName("getUser - SUCCESS")
    void test_getUser_Success() throws Exception {
        // Given
        User user = new User();

        Session session = new Session();
        session.setUser(user);

        SessionUser sessionUser = new SessionUser(
                UUID.randomUUID(),
                session,
                "email",
                user.getFirstName(),
                user.getLastName(),
                user.getPatronymic(),
                "Alek?2005"
        );

        // When
        UserDto userDtoFromMapper = new UserDto();
        when(userMapper.toDTO(user)).thenReturn(userDtoFromMapper);


        // Then
        UserDto userDto = userService.getUser(sessionUser);

        assertNotNull(userDto);
        assertEquals(userDtoFromMapper, userDto);
        verify(userMapper).toDTO(user);
    }

    @Test
    @DisplayName("getByEmail - SUCCESS")
    void test_getByEmail_Success() {
        // Given
        String email = "shumlesha@gmail.com";
        User user = new User();

        // When
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Then
        User foundUser = userService.getByEmail(email);
        assertNotNull(foundUser);
        assertEquals(user, foundUser);
        verify(userRepository).findByEmail(email);

    }


    @Test
    @DisplayName("getByEmail - FAIL (UserNotFound)")
    void test_getByEmail_UserNotFound() {
        // Given
        String email = "shumlesha@gmail.com";

        // When
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Then
        assertThrows(UserNotFoundException.class, () -> userService.getByEmail(email));
        verify(userRepository).findByEmail(email);
    }
}
