package com.senai.taskmodel.user.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.senai.taskmodel.user.entities.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseUserDTO {

    @NotBlank(message = "The name cannot be null")
    private String name;

    @Email(message = "The email provided is not valid")
    private String email;

    private String message;

    @JsonIgnore
    private Boolean success;

    public static ResponseUserDTO convertToDTO(UserEntity userEntity) {
        return ResponseUserDTO
                .builder()
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .build();
    }
}
