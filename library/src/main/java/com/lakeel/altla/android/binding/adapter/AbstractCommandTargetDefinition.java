package com.lakeel.altla.android.binding.adapter;

import android.support.annotation.NonNull;

public abstract class AbstractCommandTargetDefinition implements CommandTargetDefinition {

    private final Class<?> ownerType;

    private final String commandName;

    protected AbstractCommandTargetDefinition(@NonNull Class<?> ownerType, @NonNull String commandName) {
        this.ownerType = ownerType;
        this.commandName = commandName;
    }

    @Override
    public Class<?> getOwnerType() {
        return ownerType;
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
}
