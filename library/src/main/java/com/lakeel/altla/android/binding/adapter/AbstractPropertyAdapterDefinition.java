package com.lakeel.altla.android.binding.adapter;

import android.support.annotation.NonNull;

public abstract class AbstractPropertyAdapterDefinition implements PropertyAdapterDefinition {

    private final Class<?> ownerType;

    private final String propertyName;

    protected AbstractPropertyAdapterDefinition(@NonNull Class<?> ownerType, @NonNull String propertyName) {
        this.ownerType = ownerType;
        this.propertyName = propertyName;
    }

    @Override
    public final Class<?> getOwnerType() {
        return ownerType;
    }

    @Override
    public final String getPropertyName() {
        return propertyName;
    }
}
