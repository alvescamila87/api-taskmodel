package com.senai.taskmodel.task.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class MensagemDTO {

    private String mensagem;

    @JsonIgnore
    private Boolean success;
}
