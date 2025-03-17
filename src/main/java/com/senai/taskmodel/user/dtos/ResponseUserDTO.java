package com.senai.taskmodel.user.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.senai.taskmodel.task.dtos.TaskDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseUserDTO {

    @NotBlank(message = "The name cannot be null")
    private String name;

    @Email(message = "The email provided is not valid")
    private String email;

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<TaskDTO> taskDTOList = new ArrayList<>();

    private String message;

    @JsonIgnore
    private Boolean success;
}
