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
    public ResponseEntity<List<UserDTO>> listAllUsers() {
        List<UserDTO> listUsers = service.findAllUsers();
        return ResponseEntity.ok().body(listUsers);
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
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        service.deleteUser(email);
        return ResponseEntity.noContent().build();
    }
}
