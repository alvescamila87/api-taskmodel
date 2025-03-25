package com.senai.taskmodel.user.repositories;

import com.senai.taskmodel.user.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    private final String USER_DEFAULT_NAME = "João Batista";
    private final String USER_DEFAULT_EMAIL = "joao@gmail.com";

    private UserEntity createUser(int suffix) {
        return UserEntity.builder()
                .name(USER_DEFAULT_NAME + " " + suffix)
                .email(USER_DEFAULT_EMAIL + suffix + "@gmail.com")
                .build();
    }

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

    @Test
    void when_find_all_with_pageable_on_empty_repository_returns_empty_page() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserEntity> emptyPage = repository.findAll(pageable);

        assertTrue(emptyPage.isEmpty());
        assertEquals(0, emptyPage.getTotalElements());
        assertEquals(0, emptyPage.getTotalPages());
        assertFalse(emptyPage.hasNext());
        assertFalse(emptyPage.hasPrevious());
    }

    @Test
    void when_find_all_with_pagination_returns_correct_page() {
        int totalUsers = 15;
        List<UserEntity> usersToSave = IntStream.rangeClosed(1, totalUsers)
                .mapToObj(this::createUser)
                .toList();
        repository.saveAll(usersToSave);

        int pageSize = 5;

        Pageable firstPageRequest = PageRequest.of(0, pageSize);

        Page<UserEntity> firstPage = repository.findAll(firstPageRequest);

        assertEquals(pageSize, firstPage.getContent().size());
        assertEquals(totalUsers, firstPage.getTotalElements());
        assertEquals((int) Math.ceil((double) totalUsers / pageSize), firstPage.getTotalPages());
        assertTrue(firstPage.hasNext());
        assertFalse(firstPage.hasPrevious());
        assertEquals(USER_DEFAULT_NAME + " 1", firstPage.getContent().get(0).getName());
        assertEquals(USER_DEFAULT_EMAIL + "1@gmail.com", firstPage.getContent().get(0).getEmail());
        assertEquals(USER_DEFAULT_NAME + " 5", firstPage.getContent().get(pageSize - 1).getName());
        assertEquals(USER_DEFAULT_EMAIL + "5@gmail.com", firstPage.getContent().get(pageSize - 1).getEmail());

        Pageable secondPageRequest = PageRequest.of(1, pageSize);
        Page<UserEntity> secondPage = repository.findAll(secondPageRequest);

        assertEquals(pageSize, secondPage.getContent().size());
        assertTrue(secondPage.hasNext());
        assertTrue(secondPage.hasPrevious());
        assertEquals(USER_DEFAULT_NAME + " 6", secondPage.getContent().get(0).getName());
        assertEquals(USER_DEFAULT_EMAIL + "6@gmail.com", secondPage.getContent().get(0).getEmail());
        assertEquals(USER_DEFAULT_NAME + " 10", secondPage.getContent().get(pageSize - 1).getName());
        assertEquals(USER_DEFAULT_EMAIL + "10@gmail.com", secondPage.getContent().get(pageSize - 1).getEmail());

        Pageable lastPageRequest = PageRequest.of(2, pageSize);
        Page<UserEntity> lastPage = repository.findAll(lastPageRequest);

        assertEquals(totalUsers - (pageSize * 2), lastPage.getContent().size()); // Remaining elements
        assertFalse(lastPage.hasNext());
        assertTrue(lastPage.hasPrevious());
        assertEquals(USER_DEFAULT_NAME + " 11", lastPage.getContent().get(0).getName());
        assertEquals(USER_DEFAULT_EMAIL + "11@gmail.com", lastPage.getContent().get(0).getEmail());
        assertEquals(USER_DEFAULT_NAME + " 15", lastPage.getContent().get(lastPage.getContent().size() - 1).getName());
        assertEquals(USER_DEFAULT_EMAIL + "15@gmail.com", lastPage.getContent().get(lastPage.getContent().size() - 1).getEmail());
    }
}
