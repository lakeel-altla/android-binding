package com.lakeel.altla.android.property;

import android.support.annotation.Nullable;

public abstract class AbstractFloatProperty extends AbstractProperty {

    protected AbstractFloatProperty() {
    }

    public abstract float get();

    public abstract void set(float value);

    @Nullable
    @Override
    public final Object getValue() {
        return get();
    }

    @Override
    public final void setValue(@Nullable Object value) {
        set(value == null ? 0 : (float) value);
    }
}
