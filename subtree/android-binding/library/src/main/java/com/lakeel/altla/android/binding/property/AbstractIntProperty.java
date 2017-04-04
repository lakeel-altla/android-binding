package com.lakeel.altla.android.binding.property;

import android.support.annotation.Nullable;

public abstract class AbstractIntProperty extends BaseProperty {

    protected AbstractIntProperty() {
    }

    public abstract int get();

    public abstract void set(int value);

    @Nullable
    @Override
    public final Object getAsObject() {
        return get();
    }

    @Override
    public final void setAsObject(@Nullable Object value) {
        set(value == null ? 0 : (int) value);
    }
}
