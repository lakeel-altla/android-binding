package com.lakeel.altla.android.binding.adapter.view;

import com.lakeel.altla.android.binding.DefaultBindingModeResolver;
import com.lakeel.altla.android.binding.adapter.AbstractPropertyAdapterDefinition;
import com.lakeel.altla.android.property.BaseProperty;
import com.lakeel.altla.android.property.Property;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public final class EditTextTextAdapterDefinition extends AbstractPropertyAdapterDefinition {

    public EditTextTextAdapterDefinition() {
        super(EditText.class, "text");
    }

    @Override
    public Property createProperty(@NonNull Object owner) {
        return new Adapter((EditText) owner);
    }

    private final class Adapter extends BaseProperty implements DefaultBindingModeResolver {

        private final EditText editText;

        private final TextWatcher textWatcher;

        private Adapter(@NonNull EditText editText) {
            this.editText = editText;

            textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    raiseOnValueChanged();
                }
            };

            editText.addTextChangedListener(textWatcher);
        }

        @Nullable
        @Override
        public Object getValue() {
            Editable editable = editText.getText();
            return editable == null ? null : editable.toString();
        }

        @Override
        public void setValue(@Nullable Object value) {
            editText.setText((String) value);
        }

        @Override
        public boolean bindsTwoWayByDefault() {
            return true;
        }
    }
}
