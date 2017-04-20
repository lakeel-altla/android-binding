package com.lakeel.altla.android.binding.adapter.view;

import com.lakeel.altla.android.binding.adapter.DefaultPropertyAdapterDefinition;
import com.lakeel.altla.android.binding.adapter.PropertyAdapterDefinition;
import com.lakeel.altla.android.binding.adapter.PropertyAdapterDefinitionRegistry;
import com.lakeel.altla.android.property.Property;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class ViewPropertyAdapterFactory {

    private final PropertyAdapterDefinitionRegistry definitionRegistry = new PropertyAdapterDefinitionRegistry();

    public ViewPropertyAdapterFactory() {
        definitionRegistry.add(new EditTextTextAdapterDefinition());
        definitionRegistry.add(new RadioGroupCheckedAdapterDefinition());
        definitionRegistry.add(new CompoundButtonCheckedAdapterDefinition());
    }

    @NonNull
    public Property create(@NonNull Object owner, @NonNull String propertyName) {
        PropertyAdapterDefinition definition = find(owner.getClass(), propertyName);
        if (definition == null) {
            definition = new DefaultPropertyAdapterDefinition(owner.getClass(), propertyName);
        }

        Property property = definition.createProperty(owner);
        if (property == null) {
            throw new IllegalStateException(String.format(
                    "'%s' didn't create a property.", definition.getClass().getName()));
        }

        return property;
    }

    @Nullable
    private PropertyAdapterDefinition find(@NonNull Class<?> ownerType, @NonNull String propertyName) {
        PropertyAdapterDefinition definition = definitionRegistry.get(ownerType, propertyName);
        if (definition == null) {
            Class<?> superclass = ownerType.getSuperclass();
            return superclass == null ? null : find(superclass, propertyName);
        } else {
            return definition;
        }
    }
}
