package com.lakeel.altla.android.binding.adapter.view;

import com.lakeel.altla.android.binding.DefaultBindingModeResolver;
import com.lakeel.altla.android.binding.Property;
import com.lakeel.altla.android.binding.adapter.AbstractPropertyAdapterDefinition;
import com.lakeel.altla.android.binding.property.BaseProperty;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.CompoundButton;

public final class CompoundButtonCheckedAdapterDefinition extends AbstractPropertyAdapterDefinition {

    public CompoundButtonCheckedAdapterDefinition() {
        super(CompoundButton.class, "checked");
    }

    @Override
    public Property createProperty(@NonNull Object owner) {
        return new Adapter((CompoundButton) owner);
    }

    private final class Adapter extends BaseProperty implements DefaultBindingModeResolver {

        private final CompoundButton compoundButton;

        private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;

        private Adapter(@NonNull CompoundButton compoundButton) {
            this.compoundButton = compoundButton;

            onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    raiseOnValueChanged();
                }
            };

            compoundButton.setOnCheckedChangeListener(onCheckedChangeListener);
        }

        @Nullable
        @Override
        public Object getValue() {
            return compoundButton.isChecked();
        }

        @Override
        public void setValue(@Nullable Object value) {
            compoundButton.setChecked((boolean) value);
        }

        @Override
        public boolean bindsTwoWayByDefault() {
            return true;
        }
    }
}
