package ru.cft.template.service.impl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.template.dto.user.CreateUserRequest;
import ru.cft.template.dto.user.EditUserRequest;
import ru.cft.template.dto.user.UserDto;
import ru.cft.template.exception.user.UserAlreadyExistsException;
import ru.cft.template.exception.user.UserNotFoundException;
import ru.cft.template.mapper.UserMapper;
import ru.cft.template.models.User;
import ru.cft.template.models.Wallet;
import ru.cft.template.repository.UserRepository;
import ru.cft.template.security.SessionUser;
import ru.cft.template.service.UserService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto createUser(CreateUserRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new UserAlreadyExistsException("email", createUserRequest.getEmail());
        }

        if (userRepository.existsByPhoneNumber(createUserRequest.getPhoneNumber())) {
            throw new UserAlreadyExistsException("phoneNumber", createUserRequest.getPhoneNumber());
        }


        User user = new User();
        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());
        user.setPatronymic(createUserRequest.getPatronymic());
        user.setPhoneNumber(createUserRequest.getPhoneNumber());
        user.setEmail(createUserRequest.getEmail());
        user.setBirthDate(createUserRequest.getBirthDate());
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));

        Wallet wallet = new Wallet();
        wallet.setOwner(user);
        user.setWallet(wallet);

        return userMapper.toDTO(userRepository.save(user));

    }

    @Override
    @Transactional
    public UserDto editUser(EditUserRequest editUserRequest, SessionUser sessionUser) {
        log.info(editUserRequest.toString());
        User user = sessionUser.getSession().getUser();

        Optional.ofNullable(editUserRequest.getFirstName()).ifPresent(user::setFirstName);
        Optional.ofNullable(editUserRequest.getLastName()).ifPresent(user::setLastName);
        Optional.ofNullable(editUserRequest.getPatronymic()).ifPresent(user::setPatronymic);
        Optional.ofNullable(editUserRequest.getBirthDate()).ifPresent(user::setBirthDate);

        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserDto getUser(SessionUser sessionUser) {
        return userMapper.toDTO(sessionUser.getSession().getUser());
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }
}
