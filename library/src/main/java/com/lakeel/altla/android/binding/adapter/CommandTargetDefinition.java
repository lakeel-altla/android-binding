package com.lakeel.altla.android.binding.adapter;

import com.lakeel.altla.android.binding.CommandTarget;

import android.support.annotation.NonNull;

public interface CommandTargetDefinition {

    Class<?> getOwnerType();

    String getCommandName();

    CommandTarget createCommandTarget(@NonNull Object owner);
}
