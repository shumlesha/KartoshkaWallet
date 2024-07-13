package ru.cft.template.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.cft.template.dto.api.DefaultResponse;
import ru.cft.template.dto.user.CreateUserRequest;
import ru.cft.template.dto.user.EditUserRequest;
import ru.cft.template.dto.user.UserDto;
import ru.cft.template.dto.wallet.WalletDto;
import ru.cft.template.entity.Session;
import ru.cft.template.entity.User;
import ru.cft.template.security.JwtTokenProvider;
import ru.cft.template.security.SessionUser;
import ru.cft.template.service.UserService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.cft.template.constants.Endpoints.USERS_URL;
import static ru.cft.template.constants.Headers.AUTHORIZATION_HEADER;
import static ru.cft.template.constants.messages.ServiceMessages.*;

@WebMvcTest(UserController.class)
//@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    //@Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private String bearerToken;

    private UsernamePasswordAuthenticationToken authenticationToken;

    @BeforeEach
    void setUp() {
        bearerToken = "Bearer " + "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzaHVtbGVzaGFzQGdtYWlsLmNvbSIsImlkIjoiN2ZiMTc2MzktM2E5Ni00YWUxLTgwMGUtZjQxOTRhOWRhOWJmIiwiaWF0IjoxNzIwNjIxNDc1LCJleHAiOjE3MjA2MjUwNzV9.QoilYBMoF_Yh3Rq_s1vIiU0Jh7UAjxN2Xv2_dVm9TlVvHj5OuzN8R6xms6RS-V92";
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        UserDto userDto = new UserDto(
                UUID.randomUUID(),
                "Aleksey",
                "Shumkov",
                "Sergeevich",
                "+79061982546",
                "shumlesha@gmail.com",
                LocalDate.of(2005, 1, 21),
                LocalDateTime.now(),
                LocalDateTime.now(),
                new WalletDto(UUID.randomUUID(), 100L)
        );
        SessionUser sessionUser = new SessionUser(
                userDto.getId(),
                new Session(),
                userDto.getEmail(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getPatronymic(),
                "Alek?2005"
        );

        authenticationToken = new UsernamePasswordAuthenticationToken(sessionUser, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }


    @Test
    @DisplayName("POST /users - Success")
    void test_CreateUser_Success() throws Exception {
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

        UserDto userDto = new UserDto(
                UUID.randomUUID(),
                "Aleksey",
                "Shumkov",
                "Sergeevich",
                "+79061982546",
                "shumlesha@gmail.com",
                LocalDate.of(2005, 1, 21),
                LocalDateTime.now(),
                LocalDateTime.now(),
                new WalletDto(UUID.randomUUID(), 100L)
        );

        // When
        when(userService.createUser(createUserRequest)).thenReturn(userDto);


        // Then
        mockMvc.perform(post(USERS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserRequest)).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new DefaultResponse<>(
                                HttpStatus.OK,
                                String.format(USER_SUCCESSFULLY_CREATED, userDto.getId()),
                                userDto
                        ))))
                .andDo(print())
                .andReturn();
        verify(userService, times(1)).createUser(createUserRequest);
    }


    @Test
    @DisplayName("PATCH /users - Success")
    void test_EditUser_Success() throws Exception {
        // Given
        EditUserRequest editUserRequest = new EditUserRequest(
                "Алексей",
                "Шумков",
                "Сергеевич",
                LocalDate.of(2004, 1, 22)
        );


        UserDto userDto = new UserDto(
                UUID.randomUUID(),
                editUserRequest.getFirstName(),
                editUserRequest.getLastName(),
                editUserRequest.getPatronymic(),
                "+79061982546",
                "shumleshas@gmail.com",
                editUserRequest.getBirthDate(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                new WalletDto(UUID.randomUUID(), 100L)
        );

        // When
        when(userService.editUser(any(EditUserRequest.class), any(SessionUser.class))).thenReturn(userDto);


        // Then
        mockMvc.perform(patch(USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editUserRequest))
                        .with(csrf())

                        .header(AUTHORIZATION_HEADER, bearerToken)
                        )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new DefaultResponse<>(
                                HttpStatus.OK,
                                String.format(USER_SUCCESSFULLY_UPDATED, userDto.getId()),
                                userDto
                        ))))
                .andDo(print())
                .andReturn();
        verify(userService, times(1)).editUser(any(EditUserRequest.class), any(SessionUser.class));
    }

    @Test
    void test_getUser_Success() throws Exception {
        // Given
        UserDto userDto = new UserDto(
                UUID.randomUUID(),
                "Aleksey",
                "Shumkov",
                "Sergeevich",
                "+79061982546",
                "shumlesha@gmail.com",
                LocalDate.of(2005, 1, 21),
                LocalDateTime.now(),
                LocalDateTime.now(),
                new WalletDto(UUID.randomUUID(), 100L)
        );

        // When
        when(userService.getUser(any(SessionUser.class))).thenReturn(userDto);

        // Then
        // Then
        mockMvc.perform(get(USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .header(AUTHORIZATION_HEADER, bearerToken)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new DefaultResponse<>(
                                HttpStatus.OK,
                                String.format(USER_SUCCESSFULLY_RETRIEVED, userDto.getId()),
                                userDto
                        ))))
                .andDo(print())
                .andReturn();
        verify(userService, times(1)).getUser(any(SessionUser.class));

    }
}
