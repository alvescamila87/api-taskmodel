package com.senai.taskmodel.task.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumStatus {
    OPEN("Open"),
    IN_PROGRESS("In progress"),
    CONCLUDED("Concluded"),
    CANCELLED("Cancelled");

   private final String label;

   // Contrutor
    EnumStatus (String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

}
