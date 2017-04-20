package com.lakeel.altla.android.property;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseProperty implements Property {

    private final List<OnValueChangedListener> listeners = new ArrayList<>();

    protected BaseProperty() {
    }

    @Override
    public final void addOnValueChangedListener(@NonNull OnValueChangedListener listener) {
        listeners.add(listener);
    }

    @Override
    public final void removeOnValueChangedListener(@NonNull OnValueChangedListener listener) {
        listeners.remove(listener);
    }

    public final void raiseOnValueChanged() {
        for (OnValueChangedListener listener : listeners) {
            listener.onValueChanged(this);
        }
    }
}
