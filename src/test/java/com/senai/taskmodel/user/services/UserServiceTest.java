package com.senai.taskmodel.user.services;

import com.senai.taskmodel.task.repositories.TaskRepository;
import com.senai.taskmodel.user.dtos.ResponseUserDTO;
import com.senai.taskmodel.user.dtos.UserDTO;
import com.senai.taskmodel.user.entities.UserEntity;
import com.senai.taskmodel.user.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository repository;

    @Mock
    TaskRepository taskRepository;

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

    @Test
    void when_update_user_then_return_user_updated() {
        UserDTO updateUserDTO = UserDTO
                .builder()
                .name("David King")
                .email(USER_DEFAULT_EMAIL)
                .build();

        UserEntity updateUserEntity =  UserEntity
                .builder()
                .id(USER_DEFAULT_ID)
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .build();

        when(repository.findByEmail(USER_DEFAULT_EMAIL)).thenReturn(Optional.of(updateUserEntity));

        ResponseUserDTO responseUserDTO = service.updateUser(USER_DEFAULT_EMAIL, updateUserDTO);

        assertNotNull(responseUserDTO);
        assertEquals("David King", responseUserDTO.getName());
        assertEquals(USER_DEFAULT_EMAIL, responseUserDTO.getEmail());
        assertEquals("User has been updated", responseUserDTO.getMessage());
        assertTrue(responseUserDTO.getSuccess());

        verify(repository, times(2)).findByEmail(USER_DEFAULT_EMAIL);
        verify(repository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void when_update_user_with_wrong_email_then_return_not_found() {

        UserDTO updateUserDTO = UserDTO
                .builder()
                .name("Not exist")
                .email("not_existing_email@gmail.com")
                .build();

        when(repository.findByEmail(updateUserDTO.getEmail())).thenReturn(Optional.empty());

        ResponseUserDTO updateUserByEmail = service.updateUser("not_existing_email@gmail.com", updateUserDTO);

        assertNotNull(updateUserByEmail);
        assertEquals("User not found", updateUserByEmail.getMessage());
        assertFalse(updateUserByEmail.getSuccess());

        verify(repository, times(1)).findByEmail("not_existing_email@gmail.com");
    }

    @Test
    void when_update_user_with_existing_email_and_different_id_then_return_messagem() {

        UserDTO updateUserDTO = UserDTO
                .builder()
                .name("Rei Davi Salomão")
                .email("salomao@gmail.com")
                .build();

        UserEntity existingUser = UserEntity
                .builder()
                .id(USER_DEFAULT_ID)
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .build();

        UserEntity userWithTheSameEmail = UserEntity
                .builder()
                .id(2L)
                .name("Salomão Sucessor")
                .email("salomao@gmail.com")
                .build();

        when(repository.findByEmail(USER_DEFAULT_EMAIL)).thenReturn(Optional.of(existingUser));

        when(repository.findByEmail("salomao@gmail.com")).thenReturn(Optional.of(userWithTheSameEmail));

        ResponseUserDTO responseUserDTO = service.updateUser(USER_DEFAULT_EMAIL, updateUserDTO);

        assertNotNull(responseUserDTO);
        assertEquals("The email is already in use.", responseUserDTO.getMessage());
        assertFalse(responseUserDTO.getSuccess());

        verify(repository, times(1)).findByEmail(USER_DEFAULT_EMAIL);
        verify(repository, times(1)).findByEmail("salomao@gmail.com");
        verify(repository, never()).save(any(UserEntity.class));
    }

    @Test
    void when_update_user_with_existing_email_and_same_id_then_return_messagem() {

        UserEntity existingUser = UserEntity
                .builder()
                .id(USER_DEFAULT_ID)
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .build();

        UserDTO updateUser = UserDTO
                .builder()
                .name("Rei Davi Salomão")
                .email("salomao@gmail.com")
                .build();

        when(repository.findByEmail(USER_DEFAULT_EMAIL)).thenReturn(Optional.of(existingUser));

        when(repository.findByEmail("salomao@gmail.com")).thenReturn(Optional.empty());

        ResponseUserDTO responseUserDTO = service.updateUser(USER_DEFAULT_EMAIL, updateUser);

        assertNotNull(responseUserDTO);
        assertEquals("Rei Davi Salomão", responseUserDTO.getName());
        assertEquals("salomao@gmail.com", responseUserDTO.getEmail());
        assertEquals("User has been updated", responseUserDTO.getMessage());
        assertTrue(responseUserDTO.getSuccess());

        verify(repository, times(1)).findByEmail(USER_DEFAULT_EMAIL);
        verify(repository, times(1)).findByEmail("salomao@gmail.com");
        verify(repository, times(1)).save(any(UserEntity.class));
    }


    @Test
    void when_delete_user_with_existing_email_then_return_success_message(){

        UserEntity deleteUser = UserEntity
                .builder()
                .id(USER_DEFAULT_ID)
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .build();

        when(repository.findByEmail(USER_DEFAULT_EMAIL)).thenReturn(Optional.of(deleteUser));

        when(taskRepository.existsByUserEmail(USER_DEFAULT_EMAIL)).thenReturn(false);

        ResponseUserDTO responseUserDTO = service.deleteUser(USER_DEFAULT_EMAIL);

        assertNotNull(responseUserDTO);
        assertEquals("User has been deleted", responseUserDTO.getMessage());
        assertTrue(responseUserDTO.getSuccess());

        verify(repository, times(1)).findByEmail(USER_DEFAULT_EMAIL);
        verify(taskRepository, times(1)).existsByUserEmail(USER_DEFAULT_EMAIL);
        verify(repository, times(1)).delete(deleteUser);

    }

    @Test
    void when_delete_user_with_existing_email_then_return_tasks_related_message(){

        UserEntity deleteUser = UserEntity
                .builder()
                .id(USER_DEFAULT_ID)
                .name(USER_DEFAULT_NAME)
                .email(USER_DEFAULT_EMAIL)
                .build();

        when(repository.findByEmail(USER_DEFAULT_EMAIL)).thenReturn(Optional.of(deleteUser));

        when(taskRepository.existsByUserEmail(USER_DEFAULT_EMAIL)).thenReturn(true);

        ResponseUserDTO responseUserDTO = service.deleteUser(USER_DEFAULT_EMAIL);

        assertNotNull(responseUserDTO);
        assertEquals("User cannot be removed because he has tasks assigned to him", responseUserDTO.getMessage());
        assertFalse(responseUserDTO.getSuccess());

        verify(repository, times(1)).findByEmail(USER_DEFAULT_EMAIL);
        verify(taskRepository, times(1)).existsByUserEmail(USER_DEFAULT_EMAIL);
    }

    @Test
    void when_delete_user_with_wrong_email_then_return_not_found() {

        UserEntity deleteUser = UserEntity
                .builder()
                .name(USER_DEFAULT_NAME)
                .email("not_exist@gmail.com")
                .build();

        when(repository.findByEmail(deleteUser.getEmail())).thenReturn(Optional.empty());

        ResponseUserDTO responseUserDTO = service.deleteUser("not_exist@gmail.com");

        assertNotNull(responseUserDTO);
        assertNull(null, responseUserDTO.getName());
        assertNull(null, responseUserDTO.getEmail());
        assertEquals("User not found", responseUserDTO.getMessage());
        assertFalse(responseUserDTO.getSuccess());

        verify(repository, times(1)).findByEmail(("not_exist@gmail.com"));

    }

    @Test
    void when_get_users_returns_mapped_page() {
        Pageable pageable = PageRequest.of(0, 10);
        List<UserEntity> userEntities = List.of(
                UserEntity.builder().name(USER_DEFAULT_NAME).email(USER_DEFAULT_EMAIL).build(),
                UserEntity.builder().name("Maria Silva").email("maria@gmail.com").build()
        );
        Page<UserEntity> userEntityPage = new PageImpl<>(userEntities, pageable, userEntities.size());

        when(repository.findAll(pageable)).thenReturn(userEntityPage);

        Page<ResponseUserDTO> responseUserDTOPage = service.getUsers(pageable);

        assertNotNull(responseUserDTOPage);
        assertEquals(userEntityPage.getTotalElements(), responseUserDTOPage.getTotalElements());
        assertEquals(userEntityPage.getTotalPages(), responseUserDTOPage.getTotalPages());
        assertEquals(userEntityPage.getNumber(), responseUserDTOPage.getNumber());
        assertEquals(userEntityPage.getSize(), responseUserDTOPage.getSize());
        assertEquals(userEntities.get(0).getName(), responseUserDTOPage.getContent().get(0).getName());
        assertEquals(userEntities.get(0).getEmail(), responseUserDTOPage.getContent().get(0).getEmail());
        assertEquals(userEntities.get(1).getName(), responseUserDTOPage.getContent().get(1).getName());
        assertEquals(userEntities.get(1).getEmail(), responseUserDTOPage.getContent().get(1).getEmail());
    }

    @Test
    void when_get_users_repository_returns_empty_page() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserEntity> emptyUserEntityPage = Page.empty(pageable);

        when(repository.findAll(pageable)).thenReturn(emptyUserEntityPage);

        Page<ResponseUserDTO> responseUserDTOPage = service.getUsers(pageable);

        assertNotNull(responseUserDTOPage);
        assertEquals(0, responseUserDTOPage.getTotalElements());
        assertEquals(1, responseUserDTOPage.getTotalPages());
        assertEquals(0, responseUserDTOPage.getNumber());
        assertEquals(10, responseUserDTOPage.getSize());
        assertEquals(0, responseUserDTOPage.getContent().size());
    }

    @Test
    void when_get_users_repository_returns_page_with_null_values() {
        Pageable pageable = PageRequest.of(0, 10);
        List<UserEntity> userEntitiesWithNulls = List.of(
                UserEntity.builder().name(null).email(USER_DEFAULT_EMAIL).build(),
                UserEntity.builder().name(USER_DEFAULT_NAME).email(null).build()
        );
        Page<UserEntity> userEntityPageWithNulls = new PageImpl<>(userEntitiesWithNulls, pageable, userEntitiesWithNulls.size());

        when(repository.findAll(pageable)).thenReturn(userEntityPageWithNulls);

        Page<ResponseUserDTO> responseUserDTOPage = service.getUsers(pageable);

        assertNotNull(responseUserDTOPage);
        assertEquals(userEntityPageWithNulls.getTotalElements(), responseUserDTOPage.getTotalElements());
        assertEquals(userEntityPageWithNulls.getTotalPages(), responseUserDTOPage.getTotalPages());
        assertEquals(userEntityPageWithNulls.getNumber(), responseUserDTOPage.getNumber());
        assertEquals(userEntityPageWithNulls.getSize(), responseUserDTOPage.getSize());
        assertEquals(userEntitiesWithNulls.get(0).getName(), responseUserDTOPage.getContent().get(0).getName());
        assertEquals(userEntitiesWithNulls.get(0).getEmail(), responseUserDTOPage.getContent().get(0).getEmail());
        assertEquals(userEntitiesWithNulls.get(1).getName(), responseUserDTOPage.getContent().get(1).getName());
        assertEquals(userEntitiesWithNulls.get(1).getEmail(), responseUserDTOPage.getContent().get(1).getEmail());
    }
}
