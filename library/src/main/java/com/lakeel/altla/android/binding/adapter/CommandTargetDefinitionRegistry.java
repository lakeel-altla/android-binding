package com.lakeel.altla.android.binding.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class CommandTargetDefinitionRegistry {

    private Map<Class<?>, Map<String, CommandTargetDefinition>> definitionMap = new HashMap<>();

    public void add(@NonNull CommandTargetDefinition factory) {
        Map<String, CommandTargetDefinition> classDefinitionMap = definitionMap.get(factory.getOwnerType());
        if (classDefinitionMap == null) {
            classDefinitionMap = new HashMap<>();
            definitionMap.put(factory.getOwnerType(), classDefinitionMap);
        }

        classDefinitionMap.put(factory.getCommandName(), factory);
    }

    public void remove(@NonNull CommandTargetDefinition factory) {
        Map<String, CommandTargetDefinition> classDefinitionMap = definitionMap.get(factory.getOwnerType());
        if (classDefinitionMap != null) {
            classDefinitionMap.remove(factory.getCommandName());
        }
    }

    @Nullable
    public CommandTargetDefinition get(@NonNull Class<?> ownerType, @NonNull String commandName) {
        Map<String, CommandTargetDefinition> classDefinitionMap = definitionMap.get(ownerType);
        return classDefinitionMap == null ? null : classDefinitionMap.get(commandName);
    }
}
