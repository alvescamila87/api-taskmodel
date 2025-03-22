package com.senai.taskmodel.user.controllers;
import com.senai.taskmodel.user.dtos.ResponseUserDTO;
import com.senai.taskmodel.user.dtos.UserDTO;

import com.senai.taskmodel.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping
    public ResponseEntity<List<ResponseUserDTO>> listAllUsers() {
        List<ResponseUserDTO> listUsers = service.findAllUsers();

        if(listUsers.isEmpty()) {
            return ResponseEntity.status(404).body(listUsers);
        }
        return ResponseEntity.ok().body(listUsers);
    }

    @GetMapping("/{email}")
    public ResponseEntity<ResponseUserDTO> findUserById(@PathVariable String email) {
        ResponseUserDTO responseUserDTO = service.getUserByEmail(email);

        if(!responseUserDTO.getSuccess()){
            return ResponseEntity.status(404).body(responseUserDTO);
        }

        return ResponseEntity.ok().body(responseUserDTO);
    }

    @PostMapping
    public ResponseEntity<ResponseUserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        ResponseUserDTO newUser = service.createUser(userDTO);

        if(!newUser.getSuccess()) {
            return ResponseEntity.status(409).body(newUser);
        }
        return ResponseEntity.status(201).body(newUser);
    }

    @PutMapping("/{email}")
    public ResponseEntity<ResponseUserDTO> updateUser(@PathVariable String email, @RequestBody @Valid UserDTO userDTO) {
        ResponseUserDTO updateUser = service.updateUser(email, userDTO);

        if(!updateUser.getSuccess()) {
            return ResponseEntity.status(404).body(updateUser);
        }
        return ResponseEntity.ok().body(updateUser);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<ResponseUserDTO> deleteUser(@PathVariable String email) {
        ResponseUserDTO deleteUser = service.deleteUser(email);

        if(!deleteUser.getSuccess() && deleteUser.getMessage().equals("User not found")) {
            return ResponseEntity.status(404).body(deleteUser);
        }

        if(!deleteUser.getSuccess() && deleteUser.getMessage().equals("User cannot be removed because he has tasks assigned to him")) {
            return ResponseEntity.status(409).body(deleteUser);
        }

        return ResponseEntity.status(200).body(deleteUser);
    }
}
