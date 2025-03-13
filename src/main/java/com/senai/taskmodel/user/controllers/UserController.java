package com.senai.taskmodel.user.controllers;

import com.senai.taskmodel.user.dtos.UserDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public ResponseEntity<List<UserDTO>> listAllUsers() {
        List<UserDTO> listUsers = new ArrayList<>();

        return ResponseEntity.ok().body(listUsers);
    }
}
