package com.lakeel.altla.android.property;

import android.support.annotation.Nullable;

public abstract class AbstractDoubleProperty extends AbstractProperty {

    protected AbstractDoubleProperty() {
    }

    public abstract double get();

    public abstract void set(double value);

    @Nullable
    @Override
    public final Object getValue() {
        return get();
    }

    @Override
    public final void setValue(@Nullable Object value) {
        set(value == null ? 0 : (double) value);
    }
}
