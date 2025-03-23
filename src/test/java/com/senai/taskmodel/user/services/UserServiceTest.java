package com.senai.taskmodel.user.services;

import com.senai.taskmodel.user.dtos.ResponseUserDTO;
import com.senai.taskmodel.user.dtos.UserDTO;
import com.senai.taskmodel.user.entities.UserEntity;
import com.senai.taskmodel.user.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    UserService service;

    private final Long USER_DEFAULT_ID = 1L;
    private final String USER_DEFAULT_NAME = "Rei David";
    private final String USER_DEFAULT_EMAIL = "david@gmail.com";

    @Test
    void when_find_users_then_return_all_users() {
        List<UserEntity> listUsers = new ArrayList<>();

        UserEntity newUser1 = UserEntity
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .build();

        UserEntity newUser2 = UserEntity
                .builder()
                .name("João Batista")
                .email("joao@gmail.com")
                .build();

        listUsers.add(newUser1);
        listUsers.add(newUser2);

        when(repository.findAll()).thenReturn(listUsers);

        List<ResponseUserDTO> listUsersDTO = service.findAllUsers();

        assertNotNull(listUsersDTO);
        assertEquals(2, listUsersDTO.size());
        assertEquals(USER_DEFAULT_NAME, newUser1.getName());
        assertEquals(USER_DEFAULT_EMAIL, newUser1.getEmail());
        assertEquals("João Batista", newUser2.getName());
        assertEquals("joao@gmail.com", newUser2.getEmail());

        verify(repository, times(1)).findAll();

    }

    @Test
    void when_find_user_by_email_then_return_user() {

        UserEntity userEntity = UserEntity
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .build();

        when(repository.findByEmail(USER_DEFAULT_EMAIL)).thenReturn(Optional.of(userEntity));

        ResponseUserDTO userByEmail = service.getUserByEmail(USER_DEFAULT_EMAIL);

        assertNotNull(userByEmail);
        assertEquals(USER_DEFAULT_NAME, userByEmail.getName());
        assertEquals(USER_DEFAULT_EMAIL, userByEmail.getEmail());
        assertTrue(userByEmail.getSuccess());

        verify(repository, times(1)).findByEmail(USER_DEFAULT_EMAIL);
    }

    @Test
    void when_find_user_by_wrong_email_then_return_message_not_found() {

        when(repository.findByEmail("not_exist_email@gmail.com")).thenReturn(Optional.empty());

        ResponseUserDTO userByEmail = service.getUserByEmail("not_exist_email@gmail.com");

        assertNotNull(userByEmail);
        assertEquals("User not found", userByEmail.getMessage());
        assertFalse(userByEmail.getSuccess());

        verify(repository, times(1)).findByEmail("not_exist_email@gmail.com");

    }

    @Test
    void when_create_user_with_new_email_then_return_user() {

        UserDTO newUserDTO = UserDTO
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .build();

        when(repository.findByEmail(USER_DEFAULT_EMAIL)).thenReturn(Optional.empty());

        UserEntity userEntity = UserEntity
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .build();

        when(repository.save(any(UserEntity.class)))
                .thenReturn(userEntity);

        ResponseUserDTO newUserResponseDTO = service.createUser(newUserDTO);

        assertNotNull(newUserResponseDTO);
        assertEquals(USER_DEFAULT_NAME, newUserResponseDTO.getName());
        assertEquals(USER_DEFAULT_EMAIL, newUserResponseDTO.getEmail());
        assertEquals("User has been created.", newUserResponseDTO.getMessage());
        assertTrue(newUserResponseDTO.getSuccess());

        verify(repository, times(1)).findByEmail(USER_DEFAULT_EMAIL);
        verify(repository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void when_create_user_with_existing_email_then_return_message() {

        UserDTO newUserDTO = UserDTO
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .build();

        UserEntity userEntity = UserEntity
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .build();

        when(repository.findByEmail(USER_DEFAULT_EMAIL)).thenReturn(Optional.of(userEntity));

        ResponseUserDTO newUserResponseDTO = service.createUser(newUserDTO);

        assertNotNull(newUserResponseDTO);
        assertNull(null, newUserResponseDTO.getName());
        assertNull(null, newUserResponseDTO.getEmail());
        assertEquals("The email is already in use.", newUserResponseDTO.getMessage());
        assertFalse(newUserResponseDTO.getSuccess());

        verify(repository, times(1)).findByEmail(USER_DEFAULT_EMAIL);
    }

}
