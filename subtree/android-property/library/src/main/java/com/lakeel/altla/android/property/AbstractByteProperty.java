package com.lakeel.altla.android.property;

import android.support.annotation.Nullable;

public abstract class AbstractByteProperty extends AbstractProperty {

    protected AbstractByteProperty() {
    }

    public abstract byte get();

    public abstract void set(byte value);

    @Nullable
    @Override
    public final Object getValue() {
        return get();
    }

    @Override
    public final void setValue(@Nullable Object value) {
        set(value == null ? 0 : (byte) value);
    }
}
