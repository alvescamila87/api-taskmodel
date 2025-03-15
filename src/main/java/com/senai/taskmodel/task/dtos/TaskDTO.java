package com.senai.taskmodel.task.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.senai.taskmodel.task.enums.StatusENUM;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class TaskDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @NotBlank(message = "The title cannot be null")
    private String title;

    @NotBlank(message = "The description cannot be null")
    private String description;

    @NotNull(message = "The date task cannot be null")
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private LocalDate dateTask;

    @NotNull(message = "The status cannot be null")
    private StatusENUM statusENUM;

    @NotBlank(message = "The user email cannot be null")
    @Email(message = "The email provided is not valid")
    private String userEmail;

}
