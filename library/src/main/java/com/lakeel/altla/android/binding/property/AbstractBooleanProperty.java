package com.lakeel.altla.android.binding.property;

import android.support.annotation.Nullable;

public abstract class AbstractBooleanProperty extends BaseProperty {

    protected AbstractBooleanProperty() {
    }

    public abstract boolean get();

    public abstract void set(boolean value);

    @Nullable
    @Override
    public final Object getValue() {
        return get();
    }

    @Override
    public final void setValue(@Nullable Object value) {
        set(value == null ? false : (Boolean) value);
    }
}
