package com.delivery.core.application.commands;

import lombok.Getter;

import java.util.UUID;

@Getter
public final class AddStoragePlaceCommand {

    private UUID courierId;
    private String name;
    private Integer totalVolume;
}
