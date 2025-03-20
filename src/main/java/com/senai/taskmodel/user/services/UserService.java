package com.senai.taskmodel.user.services;

import com.senai.taskmodel.task.entities.TaskEntity;
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
                .message("User has been successfully registered.")
                .build();
    }

    public ResponseUserDTO updateUser(String email, UserDTO userDTO){

        Optional<UserEntity> userEntityEmailSearch = repository.findByEmail(email); //e-mail 1 m@e-mail

        if(userEntityEmailSearch.isEmpty()) {
            return ResponseUserDTO.builder()
                    .success(false)
                    .message("User not found")
                    .build();
        }

        Optional<UserEntity> userEntityEmailDTO = repository.findByEmail(userDTO.getEmail()); //e-mail 2 s@e-mail

        if(userEntityEmailDTO.isPresent()) { //e-mail 2 sendo utilizado para outro e-usuário
            //lógica e-mail igual e id

            if(!userEntityEmailSearch.get().getId().equals(userEntityEmailDTO.get().getId())) { // email 2 é do mesmo usuário via id
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

//        List<TaskEntity> listAllTasksByUser = taskRepository.findTasksAndEmail(email);
//
//        if(!listAllTasksByUser.isEmpty()) {
//            return ResponseUserDTO.builder()
//                    .message("User cannot be removed because he has tasks assigned to him")
//                    .success(false)
//                    .build();
//        }

        repository.delete(deleteUserEntity);

        return ResponseUserDTO.builder()
                .message("User has been successfully deleted")
                .success(true)
                .build();
    }
}
