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

    public List<UserDTO> findAllUsers() {
        List<UserDTO> listUsers = new ArrayList<>();

        List<UserEntity> listUserEntity = repository.findAll();

        for(UserEntity userEntity : listUserEntity) {
            UserDTO userDTO = new UserDTO();
            userDTO.setName(userEntity.getName());
            userDTO.setEmail(userEntity.getEmail());

            listUsers.add(userDTO);
        }

        return listUsers;
    }

        public ResponseUserDTO createUser(UserDTO userDTO) {
        ResponseUserDTO responseUserDTO = new ResponseUserDTO();

        UserEntity newUserEntity = new UserEntity();

        if(isUserAlreadyExists(userDTO)) {
            responseUserDTO.setMessage("The email is already in use.");
            responseUserDTO.setSuccess(false);
            return responseUserDTO;
        }

        newUserEntity.setName(userDTO.getName());
        newUserEntity.setEmail(userDTO.getEmail());

        repository.save(newUserEntity);

        responseUserDTO.setName(userDTO.getName());
        responseUserDTO.setEmail(userDTO.getEmail());
        responseUserDTO.setSuccess(true);
        responseUserDTO.setMessage("User has been successfully registered.");

        return responseUserDTO;
    }

    private Boolean isUserAlreadyExists(UserDTO userDTO) {
        Optional<UserEntity> userEntityEmail = repository.findByEmail(userDTO.getEmail());

        return userEntityEmail.isPresent();
    }

    public UserDTO updateUser(String email, UserDTO userDTO){
        Optional<UserEntity> userEntityEmail = repository.findByEmail(email);

        if(userEntityEmail.isEmpty()) {
            return null;
        }

        UserEntity updateUserEntity = userEntityEmail.get();
        updateUserEntity.setName(userDTO.getName());
        updateUserEntity.setEmail(userDTO.getEmail());

        repository.save(updateUserEntity);

        return userDTO;
    }

    public void deleteUser(String email) {
        Optional<UserEntity> deleteUser = repository.findByEmail(email);

        if(deleteUser.isEmpty()) {
            return;
        }

        UserEntity deleteUserEntity = deleteUser.get();

        repository.delete(deleteUserEntity);
    }

}
