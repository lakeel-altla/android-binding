package com.lakeel.altla.android.binding.adapter.view;

import com.lakeel.altla.android.binding.CommandTarget;
import com.lakeel.altla.android.binding.adapter.AbstractCommandTarget;
import com.lakeel.altla.android.binding.adapter.AbstractCommandTargetDefinition;

import android.support.annotation.NonNull;
import android.view.View;

public final class OnClickTargetDefinition extends AbstractCommandTargetDefinition {

    public OnClickTargetDefinition() {
        super(View.class, "onClick");
    }

    @Override
    public CommandTarget createCommandTarget(@NonNull Object owner) {
        return new Adapter((View) owner);
    }

    private final class Adapter extends AbstractCommandTarget {

        private final View view;

        private Adapter(@NonNull View view) {
            this.view = view;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    raiseOnCommandExecute();
                }
            });
        }

        @Override
        public void setEnabled(boolean enabled) {
            view.setEnabled(enabled);
        }
    }
}
