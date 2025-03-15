package com.senai.taskmodel.user.services;

import com.senai.taskmodel.user.dtos.UserDTO;
import com.senai.taskmodel.user.entities.UserEntity;
import com.senai.taskmodel.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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

    public UserDTO createUser(UserDTO userDTO) {
        //UserDTO newUserDTO = new UserDTO();
        UserEntity newUserEntity = new UserEntity();

        newUserEntity.setName(userDTO.getName());
        newUserEntity.setEmail(userDTO.getEmail());

        //newUserDTO.setName(userDTO.getName());
        //newUserDTO.setEmail(userDTO.getEmail());

        repository.save(newUserEntity);

        return userDTO;
    }

}
