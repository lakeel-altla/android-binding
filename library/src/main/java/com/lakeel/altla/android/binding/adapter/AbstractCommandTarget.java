package com.lakeel.altla.android.binding.adapter;

import com.lakeel.altla.android.binding.CommandTarget;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommandTarget implements CommandTarget {

    private final List<OnCommandExecuteListener> listeners = new ArrayList<>();

    @Override
    public final void addOnCommandExecuteListener(@NonNull OnCommandExecuteListener listener) {
        listeners.add(listener);
    }

    @Override
    public final void removeOnCommandExecuteListener(@NonNull OnCommandExecuteListener listener) {
        listeners.remove(listener);
    }

    protected final void raiseOnCommandExecute() {
        for (OnCommandExecuteListener listener : listeners) {
            listener.onCommandExecute();
        }
    }
}
