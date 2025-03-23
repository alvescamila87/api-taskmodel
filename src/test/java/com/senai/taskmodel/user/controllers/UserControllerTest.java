package com.senai.taskmodel.user.controllers;

import com.senai.taskmodel.user.dtos.ResponseUserDTO;
import com.senai.taskmodel.user.dtos.UserDTO;
import com.senai.taskmodel.user.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebMvcTest(UserControllerTest.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;

    private final String USER_DEFAULT_NAME = "Rei Davi";
    private final String USER_DEFAULT_EMAIL = "davi@gmail.com";

    @Test
    void when_return_user_list() throws Exception {
        ResponseUserDTO responseUserDTO = ResponseUserDTO
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .build();

        List<ResponseUserDTO> userDTOList = List.of(responseUserDTO);

        when(service.findAllUsers()).thenReturn(userDTOList);

        List<ResponseUserDTO> responseDTO = controller.listAllUsers().getBody();

        assertNotNull(responseDTO);
        assertEquals(1, responseDTO.size());
        assertEquals(USER_DEFAULT_NAME, responseDTO.get(0).getName());
        assertEquals(USER_DEFAULT_EMAIL, responseDTO.get(0).getEmail());
    }

    @Test
    void when_list_is_empty() {
        when(service.findAllUsers()).thenReturn(List.of());

        ResponseEntity<List<ResponseUserDTO>> responseDTO = controller.listAllUsers();

        assertEquals(HttpStatus.NOT_FOUND, responseDTO.getStatusCode());
        assertTrue(responseDTO.getBody().isEmpty());

    }

    @Test
    void when_find_user_by_success_email() {
        ResponseUserDTO responseUserDTO = ResponseUserDTO
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .success(true)
                .build();

        when(service.getUserByEmail(USER_DEFAULT_EMAIL)).thenReturn(responseUserDTO);

        ResponseEntity<ResponseUserDTO> responseDTO = controller.findUserById(USER_DEFAULT_EMAIL);

        assertEquals(HttpStatus.OK, responseDTO.getStatusCode());
        assertTrue(responseDTO.getBody().getSuccess());
    }

    @Test
    void when_find_user_by_not_existing_email() {
        ResponseUserDTO responseUserDTO = ResponseUserDTO
                .builder()
                .name(USER_DEFAULT_NAME)
                .email("not_existing_email@gmail.com")
                .success(false)
                .build();

        when(service.getUserByEmail(responseUserDTO.getEmail())).thenReturn(responseUserDTO);

        ResponseEntity<ResponseUserDTO> responseDTO = controller.findUserById(responseUserDTO.getEmail());

        assertEquals(HttpStatus.NOT_FOUND, responseDTO.getStatusCode());
        assertFalse(responseDTO.getBody().getSuccess());
    }

    @Test
    void when_create_user_with_success_email() {
        ResponseUserDTO responseUserDTO = ResponseUserDTO
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .success(true)
                .build();

        when(service.getUserByEmail(USER_DEFAULT_EMAIL)).thenReturn(responseUserDTO);

        when(service.createUser(any(UserDTO.class))).thenReturn(responseUserDTO);

        UserDTO userDTO = UserDTO
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .build();

        ResponseEntity<ResponseUserDTO> newUser = controller.createUser(userDTO);

        assertEquals(HttpStatus.CREATED, newUser.getStatusCode());
        assertTrue(newUser.getBody().getSuccess());
    }

    @Test
    void when_create_user_with_existing_email() {
        ResponseUserDTO responseUserDTO = ResponseUserDTO
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .success(false)
                .build();

        when(service.getUserByEmail(USER_DEFAULT_EMAIL)).thenReturn(responseUserDTO);

        when(service.createUser(any(UserDTO.class))).thenReturn(responseUserDTO);

        UserDTO userDTO = UserDTO
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .build();

        ResponseEntity<ResponseUserDTO> newUser = controller.createUser(userDTO);

        assertEquals(HttpStatus.CONFLICT, newUser.getStatusCode());
        assertFalse(newUser.getBody().getSuccess());
    }

    @Test
    void when_update_user_with_success_email() {
        ResponseUserDTO responseUserDTO = ResponseUserDTO
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .success(true)
                .build();

        when(service.getUserByEmail(USER_DEFAULT_EMAIL)).thenReturn(responseUserDTO);

        UserDTO userDTO = UserDTO
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .build();

        when(service.updateUser(eq(USER_DEFAULT_EMAIL), any(UserDTO.class))).thenReturn(responseUserDTO);

        ResponseEntity<ResponseUserDTO> updateUser = controller.updateUser(USER_DEFAULT_EMAIL, userDTO);

        assertEquals(HttpStatus.OK, updateUser.getStatusCode());
        assertEquals(USER_DEFAULT_NAME, updateUser.getBody().getName());
        assertEquals(USER_DEFAULT_EMAIL, updateUser.getBody().getEmail());
        assertTrue(updateUser.getBody().getSuccess());
    }

    @Test
    void when_update_user_with_wrong_email() {
        ResponseUserDTO responseUserDTO = ResponseUserDTO
                .builder()
                .name(USER_DEFAULT_NAME)
                .email("not_exist_email@gmail.com")
                .success(false)
                .build();

        when(service.getUserByEmail("not_exist_email@gmail.com")).thenReturn(responseUserDTO);

        when(service.updateUser(eq("not_exist_email@gmail.com"), any(UserDTO.class))).thenReturn(responseUserDTO);

        UserDTO userDTO = UserDTO
                .builder()
                .name(USER_DEFAULT_NAME)
                .email("not_exist_email@gmail.com")
                .build();

        ResponseEntity<ResponseUserDTO> newUser = controller.updateUser("not_exist_email@gmail.com", userDTO);

        assertEquals(HttpStatus.NOT_FOUND, newUser.getStatusCode());
        assertFalse(newUser.getBody().getSuccess());
    }

    @Test
    void when_update_user_with_the_same_email_in_use() {
        ResponseUserDTO responseUserDTO = ResponseUserDTO
                .builder()
                .name(USER_DEFAULT_NAME)
                .email("not_exist_email@gmail.com")
                .success(false)
                .build();

        when(service.getUserByEmail("not_exist_email@gmail.com")).thenReturn(responseUserDTO);

        when(service.updateUser(eq("not_exist_email@gmail.com"), any(UserDTO.class))).thenReturn(responseUserDTO);

        UserDTO userDTO = UserDTO
                .builder()
                .name(USER_DEFAULT_NAME)
                .email("not_exist_email@gmail.com")
                .build();

        ResponseEntity<ResponseUserDTO> newUser = controller.updateUser("not_exist_email@gmail.com", userDTO);

        assertEquals(HttpStatus.NOT_FOUND, newUser.getStatusCode());
        assertFalse(newUser.getBody().getSuccess());
    }
}
