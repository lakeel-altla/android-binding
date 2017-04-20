package com.lakeel.altla.android.binding.command;

import com.lakeel.altla.android.binding.Command;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommand implements Command {

    private final List<OnCanExecuteChangedListener> listeners = new ArrayList<>();

    @Override
    public final void addOnCanExecuteChangedListener(@NonNull OnCanExecuteChangedListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeOnCanExecuteChangedListener(@NonNull OnCanExecuteChangedListener listener) {
        listeners.remove(listener);
    }

    @UiThread
    public final void raiseOnCanExecuteChanged() {
        for (OnCanExecuteChangedListener listener : listeners) {
            listener.onCanExecuteChanged();
        }
    }
}
