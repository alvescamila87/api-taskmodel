package com.senai.taskmodel.user.services;

import com.senai.taskmodel.task.dtos.ResponseTaskDTO;
import com.senai.taskmodel.task.dtos.TaskDTO;
import com.senai.taskmodel.task.entities.TaskEntity;
import com.senai.taskmodel.task.repositories.TaskRepository;
import com.senai.taskmodel.user.dtos.ResponseUserDTO;
import com.senai.taskmodel.user.dtos.UserDTO;
import com.senai.taskmodel.user.entities.UserEntity;
import com.senai.taskmodel.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    UserRepository repository;

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

        public ResponseUserDTO createUser(UserDTO userDTO) {
        ResponseUserDTO responseUserDTO = ResponseUserDTO.builder().build();

        UserEntity newUserEntity = UserEntity.builder().build();

        if(isUserAlreadyExists(userDTO)) {
            responseUserDTO
                    .toBuilder()
                    .message("The email is already in use.")
                    .success(false)
                    .build();

            return responseUserDTO;
        }

        newUserEntity
                .toBuilder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .build();

        repository.save(newUserEntity);

        responseUserDTO
                .toBuilder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .success(true)
                .message("User has been successfully registered.")
                .build();

        return responseUserDTO;
    }

    public ResponseUserDTO updateUser(String email, UserDTO userDTO){
        ResponseUserDTO responseUserDTO = ResponseUserDTO.builder().build();

        Optional<UserEntity> userEntityEmail = repository.findByEmail(email);

        if(userEntityEmail.isEmpty()) {
            responseUserDTO
                    .toBuilder()
                    .message("User not found")
                    .success(false)
                    .build();

            return responseUserDTO;
        }

        UserEntity updateUserEntity = userEntityEmail.get();
        updateUserEntity
                .toBuilder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .build();

        repository.save(updateUserEntity);

        responseUserDTO
                .toBuilder()
                .message("User has been successfully updated")
                .success(true)
                .build();

        return responseUserDTO;
    }

    public ResponseUserDTO deleteUser(String email) {
        ResponseUserDTO responseUserDTO = ResponseUserDTO.builder().build();

        Optional<UserEntity> deleteUser = repository.findByEmail(email);

        if(deleteUser.isEmpty()) {
            responseUserDTO
                    .toBuilder()
                    .success(false)
                    .message("User not found")
                    .build();

            return responseUserDTO;
        }

        UserEntity deleteUserEntity = deleteUser.get();

        if(!deleteUserEntity.getTaskList().isEmpty()) {
            responseUserDTO
                    .toBuilder()
                    .message("User cannot be removed because he has tasks assigned to him")
                    .success(false)
                    .build();

            return responseUserDTO;
        }

        repository.delete(deleteUserEntity);

        responseUserDTO
                .toBuilder()
                .message("User has been succesfully deleted")
                .success(true)
                .build();

        return responseUserDTO;
    }

    private Boolean isUserAlreadyExists(UserDTO userDTO) {
        Optional<UserEntity> userEntityEmail = repository.findByEmail(userDTO.getEmail());

        return userEntityEmail.isPresent();
    }


}
