package com.lakeel.altla.android.binding.adapter.view;

import com.lakeel.altla.android.binding.DefaultBindingModeResolver;
import com.lakeel.altla.android.binding.Property;
import com.lakeel.altla.android.binding.adapter.AbstractPropertyAdapterDefinition;
import com.lakeel.altla.android.binding.property.BaseProperty;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.RadioGroup;

public final class RadioGroupCheckedAdapterDefinition extends AbstractPropertyAdapterDefinition {

    public RadioGroupCheckedAdapterDefinition() {
        super(RadioGroup.class, "checkedButton");
    }

    @Override
    public Property createProperty(@NonNull Object owner) {
        return new Adapter((RadioGroup) owner);
    }

    private final class Adapter extends BaseProperty implements DefaultBindingModeResolver {

        private final RadioGroup radioGroup;

        private final RadioGroup.OnCheckedChangeListener onCheckedChangeListener;

        private Adapter(@NonNull RadioGroup radioGroup) {
            this.radioGroup = radioGroup;

            onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    raiseOnValueChanged();
                }
            };

            radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        }

        @Nullable
        @Override
        public Object getValue() {
            return radioGroup.getCheckedRadioButtonId();
        }

        @Override
        public void setValue(@Nullable Object value) {
            radioGroup.check((int) value);
        }

        @Override
        public boolean bindsTwoWayByDefault() {
            return true;
        }
    }
}
