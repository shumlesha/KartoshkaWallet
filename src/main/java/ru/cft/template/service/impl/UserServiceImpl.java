package ru.cft.template.service.impl;
import lombok.RequiredArgsConstructor;
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
import ru.cft.template.entity.User;
import ru.cft.template.entity.Wallet;
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
            throw new UserAlreadyExistsException(CreateUserRequest.Fields.email, createUserRequest.getEmail());
        }

        if (userRepository.existsByPhoneNumber(createUserRequest.getPhoneNumber())) {
            throw new UserAlreadyExistsException(CreateUserRequest.Fields.phoneNumber, createUserRequest.getPhoneNumber());
        }

        User user = userMapper.toEntity(createUserRequest);
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));

        Wallet wallet = new Wallet();
        wallet.setOwner(user);
        user.setWallet(wallet);

        return userMapper.toDTO(userRepository.saveAndFlush(user));

    }

    @Override
    @Transactional
    public UserDto editUser(EditUserRequest editUserRequest, SessionUser sessionUser) {
        User user = sessionUser.getSession().getUser();

        userMapper.update(user, editUserRequest);

        return userMapper.toDTO(
                userRepository.saveAndFlush(user)
        );
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
