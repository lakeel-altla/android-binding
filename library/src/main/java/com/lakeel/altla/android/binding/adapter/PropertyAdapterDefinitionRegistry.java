package com.lakeel.altla.android.binding.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class PropertyAdapterDefinitionRegistry {

    private Map<Class<?>, Map<String, PropertyAdapterDefinition>> definitionMap = new HashMap<>();

    public void add(@NonNull PropertyAdapterDefinition factory) {
        Map<String, PropertyAdapterDefinition> classDefinitionMap = definitionMap.get(factory.getOwnerType());
        if (classDefinitionMap == null) {
            classDefinitionMap = new HashMap<>();
            definitionMap.put(factory.getOwnerType(), classDefinitionMap);
        }

        classDefinitionMap.put(factory.getPropertyName(), factory);
    }

    public void remove(@NonNull PropertyAdapterDefinition factory) {
        Map<String, PropertyAdapterDefinition> classDefinitionMap = definitionMap.get(factory.getOwnerType());
        if (classDefinitionMap != null) {
            classDefinitionMap.remove(factory.getPropertyName());
        }
    }

    @Nullable
    public PropertyAdapterDefinition get(@NonNull Class<?> ownerType, @NonNull String propertyName) {
        Map<String, PropertyAdapterDefinition> classDefinitionMap = definitionMap.get(ownerType);
        return classDefinitionMap == null ? null : classDefinitionMap.get(propertyName);
    }
}
