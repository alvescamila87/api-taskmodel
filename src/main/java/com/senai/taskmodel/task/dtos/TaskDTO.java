package com.senai.taskmodel.task.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.senai.taskmodel.task.entities.TaskEntity;
import com.senai.taskmodel.task.enums.EnumStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
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
    private EnumStatus status;

    @NotBlank(message = "The user email cannot be null")
    @Email(message = "The email provided is not valid")
    private String userEmail;

    public TaskDTO of(TaskEntity taskEntity) {
        return TaskDTO
                .builder()
                .id(taskEntity.getId())
                .title(taskEntity.getTitle())
                .description(taskEntity.getDescription())
                .dateTask(taskEntity.getDateTask())
                .status(taskEntity.getStatus())
                .userEmail(taskEntity.getUser().getEmail())
                .build();
    }
}
