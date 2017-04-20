package com.lakeel.altla.android.binding;

import android.support.annotation.NonNull;

public final class CommandBinding {

    private final CommandTarget commandTarget;

    private final Command command;

    private CommandTarget.OnCommandExecuteListener onCommandExecuteListener;

    private Command.OnCanExecuteChangedListener onCanExecuteChangedListener;

    public CommandBinding(@NonNull CommandTarget commandTarget, @NonNull Command command) {
        this.commandTarget = commandTarget;
        this.command = command;
    }

    @NonNull
    public Unbindable bind() {
        updateTarget();

        bindSource();
        bindTarget();

        return new Unbindable() {
            @Override
            public void unbind() {
                unbindSource();
                unbindTarget();
            }
        };
    }

    private void updateTarget() {
        commandTarget.setEnabled(command.canExecute());
    }

    private void bindSource() {
        onCommandExecuteListener = new CommandTarget.OnCommandExecuteListener() {
            @Override
            public void onCommandExecute() {
                command.execute();
            }
        };
        commandTarget.addOnCommandExecuteListener(onCommandExecuteListener);
    }

    private void bindTarget() {
        onCanExecuteChangedListener = new Command.OnCanExecuteChangedListener() {
            @Override
            public void onCanExecuteChanged() {
                updateTarget();
            }
        };
        command.addOnCanExecuteChangedListener(onCanExecuteChangedListener);
    }

    private void unbindSource() {
        if (onCommandExecuteListener != null) {
            commandTarget.removeOnCommandExecuteListener(onCommandExecuteListener);
            onCommandExecuteListener = null;
        }
    }

    private void unbindTarget() {
        if (onCanExecuteChangedListener != null) {
            command.removeOnCanExecuteChangedListener(onCanExecuteChangedListener);
            onCanExecuteChangedListener = null;
        }
    }
}
