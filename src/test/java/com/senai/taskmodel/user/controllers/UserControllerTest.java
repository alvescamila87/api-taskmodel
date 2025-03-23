package com.senai.taskmodel.user.controllers;

import com.senai.taskmodel.user.dtos.ResponseUserDTO;
import com.senai.taskmodel.user.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@WebMvcTest(UserControllerTest.class)
public class UserControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;

    private final String USER_DEFAULT_NAME = "Rei Davi";
    private final String USER_DEFAULT_EMAIL = "davi@gmail.com";

    @Test
    void when_return_user_list() throws Exception {
        ResponseUserDTO responseUserDTO1 = ResponseUserDTO
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .build();

        List<ResponseUserDTO> userDTOList = List.of(responseUserDTO1);

        when(service.findAllUsers()).thenReturn(userDTOList);

        List<ResponseUserDTO> listResponseUserDTO = controller.listAllUsers().getBody();

        assertNotNull(listResponseUserDTO);
        assertEquals(1, listResponseUserDTO.size());
        assertEquals(USER_DEFAULT_NAME, listResponseUserDTO.get(0).getName());
        assertEquals(USER_DEFAULT_EMAIL, listResponseUserDTO.get(0).getEmail());
    }
}
