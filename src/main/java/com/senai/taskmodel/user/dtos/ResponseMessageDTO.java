package com.senai.taskmodel.user.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMessageDTO {

    private String message;
    private Boolean success;
}
