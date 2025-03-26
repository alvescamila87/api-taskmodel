package com.senai.taskmodel.task.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseTaskDTO {

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

    private String mensagem;

    @JsonIgnore
    private Boolean success;

    public static ResponseTaskDTO convertToDTO(TaskEntity taskEntity) {
        return ResponseTaskDTO
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
