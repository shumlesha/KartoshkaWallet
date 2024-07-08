package ru.cft.template.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.cft.template.dto.api.DefaultResponse;
import ru.cft.template.dto.user.CreateUserRequest;
import ru.cft.template.dto.user.UserDto;
import ru.cft.template.dto.wallet.WalletDto;
import ru.cft.template.mapper.UserMapper;
import ru.cft.template.models.User;
import ru.cft.template.models.Wallet;
import ru.cft.template.service.UserService;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.cft.template.constants.endpoints.Endpoints.USERS_URL;
import static ru.cft.template.constants.messages.ServiceMessages.USER_SUCCESSFULLY_CREATED;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserController userController;


//    @Test
//    void test_CreateUser_Success() throws Exception {
//        CreateUserRequest createUserRequest = new CreateUserRequest(
//                "Aleksey",
//                "Shumkov",
//                "Sergeevich",
//                "+79061982546",
//                "shumlesha@gmail.com",
//                LocalDate.of(2005, 1, 21),
//                "Passsword?3241"
//        );
//
////        UserDto user = new UserDto(
////                "Aleksey",
////                "Shumkov",
////                "Sergeevich",
////                "+79061982546",
////                "shumlesha@gmail.com",
////                LocalDate.of(2005, 1, 21),
////                "Passsword?3241",
////                null,
////                new Wallet()
////        );
//
//
//        UserDto userDTO = new UserDto(
//                user.getId(),
//                user.getFirstName(),
//                user.getLastName(),
//                user.getPatronymic(),
//                user.getPhoneNumber(),
//                user.getEmail(),
//                user.getBirthDate(),
//                user.getCreatedAt(),
//                user.getModifiedAt(),
//                new WalletDto()
//        );
//
//        when(userService.createUser(createUserRequest)).thenReturn(user);
//        when(userMapper.toDTO(user)).thenReturn(userDTO);
//
//        mockMvc.perform(post(USERS_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(createUserRequest)))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(
//                        new DefaultResponse<>(
//                                HttpStatus.OK,
//                                String.format(USER_SUCCESSFULLY_CREATED, user.getId()),
//                                userDTO,
//                                null
//                        ))))
//                .andReturn();
//        verify(userService).createUser(createUserRequest);
//        verify(userMapper).toDTO(user);
//    }

}
