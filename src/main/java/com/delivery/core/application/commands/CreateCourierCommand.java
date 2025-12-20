package com.delivery.core.application.commands;

import lombok.Getter;

@Getter
public final class CreateCourierCommand {

    private String name;
    private Integer speed;

    private CreateCourierCommand() {

    }

    public CreateCourierCommand(String name, Integer speed) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }

        if (speed == null) {
            throw new IllegalArgumentException("Speed cannot be null");
        }

        if (speed < 1) {
            throw new IllegalArgumentException("Speed must be greater than zero");
        }

        this.name = name;
        this.speed = speed;
    }
}


