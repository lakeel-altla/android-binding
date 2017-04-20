package com.lakeel.altla.android.property;

import android.support.annotation.Nullable;

public abstract class AbstractLongProperty extends AbstractProperty {

    protected AbstractLongProperty() {
    }

    public abstract long get();

    public abstract void set(long value);

    @Nullable
    @Override
    public final Object getValue() {
        return get();
    }

    @Override
    public final void setValue(@Nullable Object value) {
        set(value == null ? 0 : (long) value);
    }
}
