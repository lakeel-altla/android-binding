package com.lakeel.altla.android.binding.adapter.view;

import com.lakeel.altla.android.binding.CommandTarget;
import com.lakeel.altla.android.binding.adapter.AbstractCommandTarget;
import com.lakeel.altla.android.binding.adapter.AbstractCommandTargetDefinition;

import android.support.annotation.NonNull;
import android.view.View;

public final class OnLongClickTargetDefinition extends AbstractCommandTargetDefinition {

    protected OnLongClickTargetDefinition() {
        super(View.class, "onLongClick");
    }

    @Override
    public CommandTarget createCommandTarget(@NonNull Object owner) {
        return new Adapter((View) owner);
    }

    private final class Adapter extends AbstractCommandTarget {

        private final View view;

        private Adapter(@NonNull View view) {
            this.view = view;

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    raiseOnCommandExecute();
                    return true;
                }
            });
        }

        @Override
        public void setEnabled(boolean enabled) {
            view.setEnabled(enabled);
        }
    }
}
