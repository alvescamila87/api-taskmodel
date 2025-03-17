package com.senai.taskmodel.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class UserDTO {

    @NotBlank(message = "The name cannot be null")
    private String name;

    @Email(message = "The email provided is not valid")
    private String email;
}
