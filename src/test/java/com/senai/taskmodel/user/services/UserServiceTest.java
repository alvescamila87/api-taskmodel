package com.senai.taskmodel.user.services;

import com.senai.taskmodel.user.dtos.ResponseUserDTO;
import com.senai.taskmodel.user.entities.UserEntity;
import com.senai.taskmodel.user.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    UserService service;

    private final String USER_DEFAULT_NAME = "King David";
    private final String USER_DEFAULT_EMAIL = "david@gmail.com";

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

        verify(repository, times(1)).findByEmail(USER_DEFAULT_EMAIL);
    }

}
