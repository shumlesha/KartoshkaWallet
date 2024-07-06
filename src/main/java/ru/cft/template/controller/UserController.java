package ru.cft.template.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.dto.api.DefaultResponse;
import ru.cft.template.dto.user.CreateUserModel;
import ru.cft.template.dto.user.EditUserModel;
import ru.cft.template.dto.user.UserDTO;

import ru.cft.template.mapper.UserMapper;
import ru.cft.template.models.User;
import ru.cft.template.security.SessionUser;
import ru.cft.template.service.UserService;
import ru.cft.template.util.DefaultResponseBuilder;

import static ru.cft.template.constants.endpoints.Endpoints.USERS_URL;
import static ru.cft.template.constants.messages.ServiceMessages.*;
import static ru.cft.template.constants.messages.SwaggerMessages.*;


@Slf4j
@RestController
@RequestMapping(USERS_URL)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = USERS_TAG)
public class UserController {

    UserService userService;
    UserMapper userMapper;

    /**
     * Создает нового пользователя
     *
     * @param createUserModel Модель создания пользователя
     * @return dto - (UserDTO) Созданный пользователь
     */
    @PostMapping
    @Operation(summary = USERS_CREATE_SUMMARY, description = USERS_CREATE_DESCRIPTION)
    public ResponseEntity<DefaultResponse<UserDTO>> createUser(@Validated @RequestBody CreateUserModel createUserModel) {
        User createdUser = userService.createUser(createUserModel);


        return ResponseEntity.ok(DefaultResponseBuilder.success(
                String.format(USER_SUCCESSFULLY_CREATED, createdUser.getId()),
                userMapper.toDTO(createdUser)
        ));
    }


    /**
     * Обновляет текущего пользователя
     *
     * @param editUserModel Модель обновления текущего пользователя
     * @return dto - Скорректированный пользователь
     */
    @PatchMapping
    @Operation(summary = USERS_UPDATE_SUMMARY, description = USERS_UPDATE_DESCRIPTION)
    public ResponseEntity<DefaultResponse<UserDTO>> editUser(@Validated @RequestBody EditUserModel editUserModel,
                                                             @AuthenticationPrincipal SessionUser sessionUser) {
        User editedUser = userService.editUser(editUserModel, sessionUser);

        log.info("Edited user with id: {}", editedUser.getId());

        return ResponseEntity.ok(DefaultResponseBuilder.success(
                String.format(USER_SUCCESSFULLY_UPDATED, editedUser.getId()),
                userMapper.toDTO(editedUser)
        ));
    }


    /**
     * Получить текущего пользователя
     *
     * @return dto - Текущий пользователь
     */
    @GetMapping
    @Operation(summary = USERS_GET_SUMMARY, description = USERS_GET_DESCRIPTION)
    public ResponseEntity<DefaultResponse<UserDTO>> getUser(@AuthenticationPrincipal SessionUser sessionUser) {
        User user = userService.getUser(sessionUser);
        return ResponseEntity.ok(DefaultResponseBuilder.success(
                String.format(USER_SUCCESSFULLY_RETRIEVED, user.getId()),
                userMapper.toDTO(user)
        ));
    }

}