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

    @Autowired
    TaskRepository taskRepository;

    public List<ResponseUserDTO> findAllUsers() {
        List<ResponseUserDTO> listUsers = new ArrayList<>();

        List<UserEntity> listUserEntity = repository.findAll();

        for(UserEntity userEntity : listUserEntity) {
            ResponseUserDTO responseUserDTO = new ResponseUserDTO();
            responseUserDTO.setName(userEntity.getName());
            responseUserDTO.setEmail(userEntity.getEmail());

            listUsers.add(responseUserDTO);
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
        responseUserDTO.setSuccess(true);

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

        UserEntity deleteUserEntity = deleteUser.get();

        if(!deleteUserEntity.getTaskList().isEmpty()) {
            responseUserDTO.setMessage("User cannot be removed because he has tasks assigned to him");
            responseUserDTO.setSuccess(false);
            return responseUserDTO;
        }

        repository.delete(deleteUserEntity);

        responseUserDTO.setMessage("User has been succesfully deleted");
        responseUserDTO.setSuccess(true);
        return responseUserDTO;
    }

    private Boolean isUserAlreadyExists(UserDTO userDTO) {
        Optional<UserEntity> userEntityEmail = repository.findByEmail(userDTO.getEmail());

        return userEntityEmail.isPresent();
    }


}
