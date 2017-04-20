package com.lakeel.altla.android.binding.adapter.view;

import com.lakeel.altla.android.binding.CommandTarget;
import com.lakeel.altla.android.binding.adapter.CommandTargetDefinition;
import com.lakeel.altla.android.binding.adapter.CommandTargetDefinitionRegistry;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class ViewCommandTargetFactory {

    private final CommandTargetDefinitionRegistry definitionRegistry = new CommandTargetDefinitionRegistry();

    public ViewCommandTargetFactory() {
        definitionRegistry.add(new OnClickTargetDefinition());
        definitionRegistry.add(new OnLongClickTargetDefinition());
    }

    @NonNull
    public CommandTarget create(@NonNull Object owner, @NonNull String commandName) {
        CommandTargetDefinition definition = find(owner.getClass(), commandName);
        if (definition == null) {
            throw new IllegalArgumentException(String.format(
                    "Command '%s#%s' is invalid.", owner.getClass().getName(), commandName));
        }

        CommandTarget commandTarget = definition.createCommandTarget(owner);
        if (commandTarget == null) {
            throw new IllegalStateException(String.format(
                    "'%s' didn't create a command target.", definition.getClass().getName()));
        }

        return commandTarget;
    }

    @Nullable
    private CommandTargetDefinition find(@NonNull Class<?> ownerType, @NonNull String commandName) {
        CommandTargetDefinition definition = definitionRegistry.get(ownerType, commandName);
        if (definition == null) {
            Class<?> superclass = ownerType.getSuperclass();
            return superclass == null ? null : find(superclass, commandName);
        } else {
            return definition;
        }
    }
}
