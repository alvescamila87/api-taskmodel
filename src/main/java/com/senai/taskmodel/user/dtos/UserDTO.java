package com.senai.taskmodel.user.dtos;

import com.senai.taskmodel.user.entities.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDTO {

    @NotBlank(message = "The name cannot be null")
    private String name;

    @Email(message = "The email provided is not valid")
    private String email;

    public static UserDTO of(UserEntity userEntity) {
        return UserDTO
                .builder()
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .build();
    }
}
