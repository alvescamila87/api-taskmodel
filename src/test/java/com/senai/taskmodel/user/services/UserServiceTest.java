package com.senai.taskmodel.user.services;

import com.senai.taskmodel.user.entities.UserEntity;
import com.senai.taskmodel.user.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserServiceTest {

    @Autowired
    UserRepository repository;

    private final String USER_DEFAULT_NAME = "João Batista";
    private final String USER_DEFAULT_EMAIL = "joao@gmail.com";

    @Test
    void when_save_user() {
        UserEntity userEntity = UserEntity
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .build();

        UserEntity newUser = repository.save(userEntity);

        assertNotNull(newUser.getId());
        assertEquals(USER_DEFAULT_NAME, newUser.getName());
    }

    @Test
    void when_find_user_by_email_() {
        UserEntity userEntity = UserEntity
                .builder()
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .build();

        repository.save(userEntity);

        Optional<UserEntity> userByEmail = repository.findByEmail(USER_DEFAULT_EMAIL);

        assertTrue(userByEmail.isPresent());
        assertEquals(USER_DEFAULT_NAME, userByEmail.get().getName());
    }
}
