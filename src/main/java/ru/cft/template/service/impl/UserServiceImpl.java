package ru.cft.template.service.impl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.template.dto.user.CreateUserModel;
import ru.cft.template.dto.user.EditUserModel;
import ru.cft.template.exception.user.UserAlreadyExistsException;
import ru.cft.template.exception.user.UserNotFoundException;
import ru.cft.template.models.User;
import ru.cft.template.models.Wallet;
import ru.cft.template.repository.UserRepository;
import ru.cft.template.security.SessionUser;
import ru.cft.template.service.UserService;

import java.util.Optional;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User createUser(CreateUserModel createUserModel) {
        if (userRepository.existsByEmail(createUserModel.getEmail())) {
            throw new UserAlreadyExistsException("email", createUserModel.getEmail());
        }

        if (userRepository.existsByPhoneNumber(createUserModel.getPhoneNumber())) {
            throw new UserAlreadyExistsException("phoneNumber", createUserModel.getPhoneNumber());
        }


        User user = new User();
        user.setFirstName(createUserModel.getFirstName());
        user.setLastName(createUserModel.getLastName());
        user.setPatronymic(createUserModel.getPatronymic());
        user.setPhoneNumber(createUserModel.getPhoneNumber());
        user.setEmail(createUserModel.getEmail());
        user.setBirthDate(createUserModel.getBirthDate());
        user.setPassword(passwordEncoder.encode(createUserModel.getPassword()));

        Wallet wallet = new Wallet();
        wallet.setOwner(user);
        user.setWallet(wallet);

        return userRepository.save(user);

    }

    @Override
    @Transactional
    public User editUser(EditUserModel editUserModel, SessionUser sessionUser) {
        log.info(editUserModel.toString());
        User user = sessionUser.getSession().getUser();

        Optional.ofNullable(editUserModel.getFirstName()).ifPresent(user::setFirstName);
        Optional.ofNullable(editUserModel.getLastName()).ifPresent(user::setLastName);
        Optional.ofNullable(editUserModel.getPatronymic()).ifPresent(user::setPatronymic);
        Optional.ofNullable(editUserModel.getBirthDate()).ifPresent(user::setBirthDate);

        return userRepository.save(user);
    }

    @Override
    public User getUser(SessionUser sessionUser) {
        return sessionUser.getSession().getUser();
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }
}
