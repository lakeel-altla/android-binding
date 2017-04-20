package com.lakeel.altla.android.binding.adapter;

import com.lakeel.altla.android.binding.PropertyNameResolver;
import com.lakeel.altla.android.property.BaseProperty;
import com.lakeel.altla.android.property.Property;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class DefaultPropertyAdapterDefinition extends AbstractPropertyAdapterDefinition {

    private final Class<?> propertyType;

    private Method readMethod;

    private Method writeMethod;

    public DefaultPropertyAdapterDefinition(@NonNull Class<?> ownerType, @NonNull String propertyName) {
        super(ownerType, propertyName);

        try {
            readMethod = ownerType.getMethod(PropertyNameResolver.resolveReadMethodNameByNonBoolean(propertyName));
        } catch (NoSuchMethodException e) {
            try {
                readMethod = ownerType.getMethod(PropertyNameResolver.resolveReadMethodNameByBoolean(propertyName));
            } catch (NoSuchMethodException e1) {
                readMethod = null;
            }
        }

        if (readMethod == null || readMethod.getReturnType() == Void.class) {
            throw new IllegalArgumentException(String.format("No read method exists: propertyName = %s", propertyName));
        }

        propertyType = readMethod.getReturnType();

        try {
            writeMethod = ownerType.getMethod(PropertyNameResolver.resolveWriteMethodName(propertyName), propertyType);
        } catch (NoSuchMethodException e) {
            writeMethod = null;
        }
    }

    @Override
    public Property createProperty(@NonNull Object owner) {
        return new Adapter(owner);
    }

    private final class Adapter extends BaseProperty {

        private final Object owner;

        private Adapter(@NonNull Object owner) {
            this.owner = owner;
        }

        @Nullable
        @Override
        public Object getValue() {
            try {
                return readMethod.invoke(owner);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void setValue(@Nullable Object value) {
            if (writeMethod == null) {
                throw new IllegalStateException(String.format(
                        "Property '%s#%s' is read-only.", getOwnerType().getName(), getPropertyName()));
            }

            try {
                writeMethod.invoke(owner, value);
                raiseOnValueChanged();
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
