package com.lakeel.altla.android.property;

import android.support.annotation.Nullable;

public abstract class AbstractCharProperty extends AbstractProperty {

    protected AbstractCharProperty() {
    }

    public abstract char get();

    public abstract void set(char value);

    @Nullable
    @Override
    public final Object getValue() {
        return get();
    }

    @Override
    public final void setValue(@Nullable Object value) {
        set(value == null ? Character.MIN_VALUE : (char) value);
    }
}
