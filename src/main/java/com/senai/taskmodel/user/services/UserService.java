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

    public ResponseUserDTO updateUser(String email, UserDTO userDTO){
        ResponseUserDTO responseUserDTO = new ResponseUserDTO();

        Optional<UserEntity> userEntityEmail = repository.findByEmail(email);

        if(userEntityEmail.isEmpty()) {
            responseUserDTO.setMessage("User not found");
            responseUserDTO.setSuccess(false);
            return responseUserDTO;
        }

        UserEntity updateUserEntity = userEntityEmail.get();
        updateUserEntity.setName(userDTO.getName());
        updateUserEntity.setEmail(userDTO.getEmail());

        repository.save(updateUserEntity);

        responseUserDTO.setMessage("User has been successfully updated");
        responseUserDTO.setSuccess(false);

        return responseUserDTO;
    }

    public ResponseUserDTO deleteUser(String email) {
        ResponseUserDTO responseUserDTO = new ResponseUserDTO();

        Optional<UserEntity> deleteUser = repository.findByEmail(email);

        if(deleteUser.isEmpty()) {
            responseUserDTO.setSuccess(false);
            responseUserDTO.setMessage("User not found");
            return responseUserDTO;
        }

        if(!deleteUser.get().getTaskList().isEmpty()) {
            responseUserDTO.setSuccess(false);
            responseUserDTO.setMessage("User cannot be removed because he has tasks related");
            return responseUserDTO;
        }

        UserEntity deleteUserEntity = deleteUser.get();
        repository.delete(deleteUserEntity);

        responseUserDTO.setMessage("User has been succesfully deleted");
        responseUserDTO.setSuccess(true);
        return responseUserDTO;
    }

}
