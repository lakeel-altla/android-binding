package com.lakeel.altla.android.binding;

import android.support.annotation.NonNull;

public interface CommandTarget {

    void addOnCommandExecuteListener(@NonNull OnCommandExecuteListener listener);

    void removeOnCommandExecuteListener(@NonNull OnCommandExecuteListener listener);

    void setEnabled(boolean enabled);

    interface OnCommandExecuteListener {

        void onCommandExecute();
    }
}
