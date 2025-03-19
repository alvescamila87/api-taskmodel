package com.senai.taskmodel.user.services;

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

        if(isUserAlreadyExists(userDTO)) {
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
                .message("User has been successfully registered.")
                .build();
    }

    public ResponseUserDTO updateUser(String email, UserDTO userDTO){

        Optional<UserEntity> userEntityEmail = repository.findByEmail(email);

        if(userEntityEmail.isEmpty()) {
            return ResponseUserDTO.builder()
                    .message("User not found")
                    .success(false)
                    .build();
        }

        UserEntity updateUserEntity = userEntityEmail.get();
        updateUserEntity.setEmail(userDTO.getEmail());
        updateUserEntity.setName(userDTO.getName());

        repository.save(updateUserEntity);

        return ResponseUserDTO.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .message("User has been successfully updated")
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

        UserEntity deleteUserEntity = deleteUser.get();

//        if(!deleteUserEntity.getTaskList().isEmpty()) {
//            responseUserDTO
//                    .toBuilder()
//                    .message("User cannot be removed because he has tasks assigned to him")
//                    .success(false)
//                    .build();
//
//            return responseUserDTO;
//        }

        repository.delete(deleteUserEntity);

        return ResponseUserDTO.builder()
                .message("User has been successfully deleted")
                .success(true)
                .build();
    }

    private Boolean isUserAlreadyExists(UserDTO userDTO) {
        Optional<UserEntity> userEntityEmail = repository.findByEmail(userDTO.getEmail());

        return userEntityEmail.isPresent();
    }
}
