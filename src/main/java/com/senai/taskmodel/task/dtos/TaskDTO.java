package com.senai.taskmodel.task.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateTask;

}
