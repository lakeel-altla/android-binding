package com.lakeel.altla.android.binding.adapter;

import com.lakeel.altla.android.binding.Property;

import android.support.annotation.NonNull;

public interface PropertyAdapterDefinition {

    Class<?> getOwnerType();

    String getPropertyName();

    Property createProperty(@NonNull Object owner);
}
