package com.senai.taskmodel.user.controllers;

import com.senai.taskmodel.task.repositories.TaskRepository;
import com.senai.taskmodel.user.dtos.ResponseUserDTO;
import com.senai.taskmodel.user.dtos.UserDTO;
import com.senai.taskmodel.user.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@WebMvcTest(UserControllerTest.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;

    @Mock
    private TaskRepository taskRepository;

    private final String USER_DEFAULT_NAME = "Rei Davi";
    private final String USER_DEFAULT_EMAIL = "davi@gmail.com";

    @Test
    void when_return_user_list_pageable_success() throws Exception {
        ResponseUserDTO responseUserDTO = ResponseUserDTO
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .build();

        List<ResponseUserDTO> userDTOList = List.of(responseUserDTO);
        Page<ResponseUserDTO> userDTOPage = new PageImpl<>(userDTOList);
        Pageable pageable = PageRequest.of(0, 10);

        when(service.getUsers(pageable)).thenReturn(userDTOPage);

        ResponseEntity<Page<ResponseUserDTO>> responseEntity = controller.getAllUsers(pageable);
        Page<ResponseUserDTO> responseUserDTOPage = responseEntity.getBody();

        assertNotNull(responseUserDTOPage);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseUserDTOPage.getTotalElements());
        assertEquals(1, responseUserDTOPage.getContent().size());
        assertEquals(USER_DEFAULT_NAME, responseUserDTOPage.getContent().get(0).getName());
        assertEquals(USER_DEFAULT_EMAIL, responseUserDTOPage.getContent().get(0).getEmail());
    }

    @Test
    void when_page_is_empty_in_paginator() {
        Pageable pageable = PageRequest.of(0, 10);
        when(service.getUsers(pageable)).thenReturn(Page.empty());

        ResponseEntity<Page<ResponseUserDTO>> responseEntity = controller.getAllUsers(pageable);
        Page<ResponseUserDTO> responsePageDTO = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responsePageDTO.isEmpty());

    }

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

        ResponseEntity<ResponseUserDTO> responseDTO = controller.findUserByEmail(USER_DEFAULT_EMAIL);

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

        ResponseEntity<ResponseUserDTO> responseDTO = controller.findUserByEmail(responseUserDTO.getEmail());

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
    void when_delete_user_by_email_then_return_successfully() {
        ResponseUserDTO responseUserDTO = ResponseUserDTO
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .message("User has been deleted")
                .success(true)
                .build();

        when(service.deleteUser(USER_DEFAULT_EMAIL)).thenReturn(responseUserDTO);

        ResponseEntity<ResponseUserDTO> deleteUser = controller.deleteUser(USER_DEFAULT_EMAIL);

        assertEquals(HttpStatus.OK, deleteUser.getStatusCode());
        assertTrue(responseUserDTO.getSuccess());
        assertEquals("User has been deleted", deleteUser.getBody().getMessage());
    }


    @Test
    void when_delete_user_with_wrong_email_then_return_not_found() {
        ResponseUserDTO responseUserDTO = ResponseUserDTO
                .builder()
                .name("Not exist user")
                .email("not_exist_email@gmail.com")
                .message("User not found")
                .success(false)
                .build();

        when(service.deleteUser("not_exist_email@gmail.com")).thenReturn(responseUserDTO);

        ResponseEntity<ResponseUserDTO> deleteUser = controller.deleteUser(responseUserDTO.getEmail());

        assertEquals(HttpStatus.NOT_FOUND, deleteUser.getStatusCode());
        assertFalse(responseUserDTO.getSuccess());
        assertEquals("User not found", deleteUser.getBody().getMessage());
    }

    @Test
    void when_delete_user_with_tasks_related_then_return_message() {

        ResponseUserDTO responseUserDTO = ResponseUserDTO
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .message("User cannot be removed because he has tasks assigned to him")
                .success(false)
                .build();

        when(service.deleteUser(USER_DEFAULT_EMAIL)).thenReturn(responseUserDTO);

        ResponseEntity<ResponseUserDTO> deleteUser = controller.deleteUser(USER_DEFAULT_EMAIL);

        assertEquals(HttpStatus.CONFLICT, deleteUser.getStatusCode());
        assertEquals("User cannot be removed because he has tasks assigned to him", deleteUser.getBody().getMessage());
        assertFalse(deleteUser.getBody().getSuccess());

        verify(service, times(1)).deleteUser(USER_DEFAULT_EMAIL);
    }

}
