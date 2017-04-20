package com.lakeel.altla.android.binding.command;

import com.lakeel.altla.android.binding.CommandCanExecuteDelegate;
import com.lakeel.altla.android.binding.CommandExecuteDelegate;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class RelayCommand extends AbstractCommand {

    private final CommandExecuteDelegate executeDelegate;

    private final CommandCanExecuteDelegate commandCanExecuteDelegate;

    public RelayCommand(@NonNull CommandExecuteDelegate executeDelegate) {
        this(executeDelegate, null);
    }

    public RelayCommand(@NonNull CommandExecuteDelegate executeDelegate,
                        @Nullable CommandCanExecuteDelegate commandCanExecuteDelegate) {
        this.executeDelegate = executeDelegate;
        this.commandCanExecuteDelegate = commandCanExecuteDelegate;
    }

    @Override
    public final void execute() {
        executeDelegate.execute();
    }

    @Override
    public final boolean canExecute() {
        return commandCanExecuteDelegate == null || commandCanExecuteDelegate.canExecute();
    }

}
