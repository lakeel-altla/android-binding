package com.lakeel.altla.android.property;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface Property {

    @Nullable
    Object getValue();

    void setValue(@Nullable Object value);

    void addOnValueChangedListener(@NonNull OnValueChangedListener listener);

    void removeOnValueChangedListener(@NonNull OnValueChangedListener listener);

    void clearOnValueChangedListeners();

    interface OnValueChangedListener {

        void onValueChanged(@NonNull Property sender);
    }
}
