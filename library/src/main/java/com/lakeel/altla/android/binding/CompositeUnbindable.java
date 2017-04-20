package com.lakeel.altla.android.binding;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public final class CompositeUnbindable implements Unbindable {

    private final List<Unbindable> unbindables = new ArrayList<>();

    public void add(@NonNull Unbindable unbindable) {
        unbindables.add(unbindable);
    }

    @Override
    public void unbind() {
        for (Unbindable unbindable : unbindables) {
            unbindable.unbind();
        }
    }
}
