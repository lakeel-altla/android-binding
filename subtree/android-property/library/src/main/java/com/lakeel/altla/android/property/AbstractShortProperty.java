package com.lakeel.altla.android.property;

import android.support.annotation.Nullable;

public abstract class AbstractShortProperty extends AbstractProperty {

    protected AbstractShortProperty() {
    }

    public abstract short get();

    public abstract void set(short value);

    @Nullable
    @Override
    public final Object getValue() {
        return get();
    }

    @Override
    public final void setValue(@Nullable Object value) {
        set(value == null ? 0 : (short) value);
    }
}
