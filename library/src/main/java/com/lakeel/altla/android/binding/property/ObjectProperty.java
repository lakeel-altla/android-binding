package com.lakeel.altla.android.binding.property;

import android.support.annotation.Nullable;

import java.util.Objects;

public class ObjectProperty<T> extends AbstractObjectProperty<T> {

    private T value;

    public ObjectProperty() {
    }

    public ObjectProperty(T value) {
        this.value = value;
    }

    @Nullable
    @Override
    public T get() {
        return value;
    }

    @Override
    public void set(@Nullable T value) {
        if (!Objects.equals(this.value, value)) {
            this.value = value;
            raiseOnValueChanged();
        }
    }
}
