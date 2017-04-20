package com.lakeel.altla.android.property;

import android.support.annotation.Nullable;

public abstract class AbstractIntProperty extends AbstractProperty {

    protected AbstractIntProperty() {
    }

    public abstract int get();

    public abstract void set(int value);

    @Nullable
    @Override
    public final Object getValue() {
        return get();
    }

    @Override
    public final void setValue(@Nullable Object value) {
        set(value == null ? 0 : (int) value);
    }
}
