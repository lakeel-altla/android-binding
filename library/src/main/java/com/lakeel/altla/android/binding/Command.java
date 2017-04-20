package com.lakeel.altla.android.binding;

import android.support.annotation.NonNull;

public interface Command {

    void execute();

    boolean canExecute();

    void addOnCanExecuteChangedListener(@NonNull OnCanExecuteChangedListener listener);

    void removeOnCanExecuteChangedListener(@NonNull OnCanExecuteChangedListener listener);

    interface OnCanExecuteChangedListener {

        void onCanExecuteChanged();
    }
}
