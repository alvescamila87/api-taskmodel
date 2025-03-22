package com.senai.taskmodel.user.services;

import com.senai.taskmodel.task.repositories.TaskRepository;
import com.senai.taskmodel.user.dtos.ResponseUserDTO;
import com.senai.taskmodel.user.dtos.UserDTO;
import com.senai.taskmodel.user.entities.UserEntity;
import com.senai.taskmodel.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    TaskRepository taskRepository;

    public List<ResponseUserDTO> findAllUsers() {
        List<ResponseUserDTO> listUsers = new ArrayList<>();

        List<UserEntity> listUserEntity = repository.findAll();

        for(UserEntity userEntity : listUserEntity) {
            ResponseUserDTO responseUserDTO = ResponseUserDTO
                    .builder()
                    .email(userEntity.getEmail())
                    .name(userEntity.getName())
                    .build();

            listUsers.add(responseUserDTO);
        }
        return listUsers;
    }

    public ResponseUserDTO getUserByEmail(String email) {
        Optional<UserEntity> userEntity = repository.findByEmail(email);

        if(userEntity.isEmpty()) {

            return ResponseUserDTO
                    .builder()
                    .message("User not found")
                    .success(false)
                    .build();
        }

        return ResponseUserDTO
                .builder()
                .name(userEntity.get().getName())
                .email(userEntity.get().getEmail())
                .success(true)
                .build();
    }

    public ResponseUserDTO createUser(UserDTO userDTO) {

        Optional<UserEntity> userEntityEmail = repository.findByEmail(userDTO.getEmail());

        if(userEntityEmail.isPresent()) {
            return ResponseUserDTO.builder()
                    .message("The email is already in use.")
                    .success(false)
                    .build();
        }

        UserEntity newUserEntity = UserEntity.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .build();

        repository.save(newUserEntity);

        return ResponseUserDTO.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .success(true)
                .message("User has been created.")
                .build();
    }

    public ResponseUserDTO updateUser(String email, UserDTO userDTO){

        Optional<UserEntity> userEntityEmailSearch = repository.findByEmail(email);

        if(userEntityEmailSearch.isEmpty()) {
            return ResponseUserDTO.builder()
                    .success(false)
                    .message("User not found")
                    .build();
        }

        Optional<UserEntity> userEntityEmailDTO = repository.findByEmail(userDTO.getEmail());

        if(userEntityEmailDTO.isPresent()) {

            if(!userEntityEmailSearch.get().getId().equals(userEntityEmailDTO.get().getId())) {
                return ResponseUserDTO.builder()
                        .message("The email is already in use.")
                        .success(false)
                        .build();
            }
        }

        UserEntity updateUserEntity = userEntityEmailSearch.get();
        updateUserEntity.setName(userDTO.getName());
        updateUserEntity.setEmail(userDTO.getEmail());

        repository.save(updateUserEntity);

        return ResponseUserDTO.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .message("User has been updated")
                .success(true)
                .build();
    }

    public ResponseUserDTO deleteUser(String email) {

        Optional<UserEntity> deleteUser = repository.findByEmail(email);

        if(deleteUser.isEmpty()) {
            return ResponseUserDTO.builder()
                    .success(false)
                    .message("User not found")
                    .build();
        }

        final var hasTaskRelated = taskRepository.existsByUserEmail(email);

        if(hasTaskRelated) {
            return ResponseUserDTO.builder()
                    .message("User cannot be removed because he has tasks assigned to him")
                    .success(false)
                    .build();
        }

        UserEntity deleteUserEntity = deleteUser.get();
        repository.delete(deleteUserEntity);

        return ResponseUserDTO.builder()
                .message("User has been deleted")
                .success(true)
                .build();
    }
}
