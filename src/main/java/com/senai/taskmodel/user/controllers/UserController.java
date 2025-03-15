package com.senai.taskmodel.user.controllers;

import com.senai.taskmodel.user.dtos.UserDTO;

import com.senai.taskmodel.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        UserDTO newUser = service.createUser(userDTO);

        return ResponseEntity.ok().body(newUser);
    }

    @PutMapping("/{email}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String email, @RequestBody @Valid UserDTO userDTO) {
        UserDTO updateUser = new UserDTO();

        return ResponseEntity.ok().body(updateUser);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable String email) {
        UserDTO deleteUser = new UserDTO();

        return ResponseEntity.status(204).body(deleteUser);
    }
}
