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
import ru.cft.template.dto.session.CreateSessionRequest;
import ru.cft.template.dto.session.RefreshSessionRequest;
import ru.cft.template.dto.session.SessionDto;
import ru.cft.template.mapper.SessionMapper;
import ru.cft.template.security.SessionUser;
import ru.cft.template.service.SessionAuthService;
import ru.cft.template.service.SessionService;
import ru.cft.template.util.DefaultResponseBuilder;

import static ru.cft.template.constants.endpoints.Endpoints.*;
import static ru.cft.template.constants.messages.ServiceMessages.*;
import static ru.cft.template.constants.messages.SwaggerMessages.*;


@Slf4j
@RestController
@RequestMapping(SESSIONS_URL)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = SESSIONS_TAG)
public class SessionController {

    SessionService sessionService;
    SessionAuthService sessionAuthService;

    /**
     * Создает сессию
     *
     * @param createSessionRequest запрос на создание сессии
     * @return dto сессии
     */
    @PostMapping
    @Operation(summary = SESSIONS_CREATE_SUMMARY, description = SESSIONS_CREATE_DESCRIPTION)
    public ResponseEntity<DefaultResponse<SessionDto>> createSession(@Validated @RequestBody CreateSessionRequest createSessionRequest) {
        SessionDto createdSession = sessionAuthService.createSession(createSessionRequest);


        return ResponseEntity.ok(DefaultResponseBuilder.success(
                String.format(SESSION_SUCCESSFULLY_CREATED),
                createdSession
        ));
    }


    /**
     * Завершает сессию принудительно
     *
     * @param sessionUser AuthenticationPrincipal (текущий пользователь)
     * @return Сообщение о выходе из сессии
     */
    @PostMapping(LOGOUT)
    @Operation(summary = SESSIONS_LOGOUT_SUMMARY, description = SESSIONS_LOGOUT_DESCRIPTION)
    public ResponseEntity<DefaultResponse<?>> logoutSession(@AuthenticationPrincipal SessionUser sessionUser) {
        sessionService.logoutSession(sessionUser);

        return ResponseEntity.ok(DefaultResponseBuilder.success(
                String.format(SESSION_SUCCESSFULLY_LOGOUT)
        ));
    }


    /**
     * Получение текущий сессии
     *
     * @param sessionUser AuthenticationPrincipal (пользователь текущей сессии)
     * @return dto информация о текущей сессии
     */
    @GetMapping
    @Operation(summary = SESSIONS_GET_SUMMARY, description = SESSIONS_GET_DESCRIPTION)
    public ResponseEntity<DefaultResponse<SessionDto>> getCurrentSession(@AuthenticationPrincipal SessionUser sessionUser) {
        SessionDto session = sessionAuthService.getCurrentSession(sessionUser);

        return ResponseEntity.ok(DefaultResponseBuilder.success(
                String.format(SESSION_SUCCESSFULLY_RETRIEVED, sessionUser.getLastName(), sessionUser.getFirstName()),
                session
        ));
    }

    /**
     * Обновление текущей сессии
     *
     * @param refreshSessionRequest запрос на обновление сессии
     * @return dto сессии
     */
    @PostMapping(REFRESH)
    @Operation(summary = SESSIONS_REFRESH_SUMMARY, description = SESSIONS_REFRESH_DESCRIPTION)
    public ResponseEntity<DefaultResponse<SessionDto>> refreshSession(@RequestBody RefreshSessionRequest refreshSessionRequest) {
        SessionDto session = sessionAuthService.refreshSession(refreshSessionRequest);

        return ResponseEntity.ok(DefaultResponseBuilder.success(
                String.format(SESSION_SUCCESSFULLY_REFRESHED),
                session
        ));
    }
}