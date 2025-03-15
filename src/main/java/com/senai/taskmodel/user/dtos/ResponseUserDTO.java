package com.senai.taskmodel.user.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.senai.taskmodel.task.dtos.TaskDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseUserDTO {

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @NotBlank(message = "The name cannot be null")
    private String name;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @Email(message = "The email provided is not valid")
    private String email;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<TaskDTO> taskDTOList;

    private String message;

    @JsonIgnore
    private Boolean success;
}
