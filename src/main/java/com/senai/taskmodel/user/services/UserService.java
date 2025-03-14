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

        for(UserEntity user : listUserEntity) {
            UserDTO dto = new UserDTO();
            dto.setEmail(user.getEmail());
            dto.setName(user.getName());

            listUsers.add(dto);
        }

        return listUsers;
    }
}
