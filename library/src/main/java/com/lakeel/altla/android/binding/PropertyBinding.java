package com.lakeel.altla.android.binding;

import android.support.annotation.NonNull;

import java.util.Objects;

public final class PropertyBinding {

    private final Property target;

    private final Property source;

    private BindingMode mode = BindingMode.DEFAULT;

    private Converter converter = Converter.EMPTY;

    private boolean targetUpdateSuppressed;

    private boolean sourceUpdateSuppressed;

    private Property.OnValueChangedListener targetOnValueChangedListener;

    private Property.OnValueChangedListener sourceOnValueChangedListener;

    public PropertyBinding(@NonNull Property target, @NonNull Property source) {
        this.target = target;
        this.source = source;
    }

    @NonNull
    public PropertyBinding mode(@NonNull BindingMode mode) {
        this.mode = mode;
        return this;
    }

    @NonNull
    public PropertyBinding converter(@NonNull Converter converter) {
        this.converter = converter;
        return this;
    }

    @NonNull
    public Unbindable bind() {
        BindingMode mode = resolveMode();

        if (mode == BindingMode.ONE_WAY || mode == BindingMode.TWO_WAY || mode == BindingMode.ONE_TIME) {
            updateTarget();
        }

        if (mode == BindingMode.ONE_WAY_TO_SOURCE) {
            updateSource();
        }

        if (mode == BindingMode.ONE_WAY || mode == BindingMode.TWO_WAY) {
            bindTarget();
        }

        if (mode == BindingMode.TWO_WAY || mode == BindingMode.ONE_WAY_TO_SOURCE) {
            bindSource();
        }

        return new Unbindable() {
            @Override
            public void unbind() {
                unbindTarget();
                unbindSource();
            }
        };
    }

    @NonNull
    private BindingMode resolveMode() {
        if (mode == BindingMode.DEFAULT) {
            boolean bindsTwoWayByDefault = false;
            if (target instanceof DefaultBindingModeResolver) {
                bindsTwoWayByDefault = ((DefaultBindingModeResolver) target).bindsTwoWayByDefault();
            }

            return bindsTwoWayByDefault ? BindingMode.TWO_WAY : BindingMode.ONE_WAY;
        } else {
            return mode;
        }
    }

    private void updateTarget() {
        sourceUpdateSuppressed = true;
        if (!targetUpdateSuppressed) {
            updateTargetCore();
        }
        sourceUpdateSuppressed = false;
    }

    private void updateSource() {
        targetUpdateSuppressed = true;
        if (!sourceUpdateSuppressed) {
            updateSourceCore();
        }
        targetUpdateSuppressed = false;
    }

    private void updateTargetCore() {
        Object value = converter.convert(source.getValue());
        if (!Objects.equals(target.getValue(), value)) {
            target.setValue(value);
        }
    }

    private void updateSourceCore() {
        Object value = converter.convertBack(target.getValue());
        if (!Objects.equals(source.getValue(), value)) {
            source.setValue(value);
        }
    }

    private void bindSource() {
        targetOnValueChangedListener = new Property.OnValueChangedListener() {
            @Override
            public void onValueChanged(@NonNull Property sender) {
                updateSource();
            }
        };
        target.addOnValueChangedListener(targetOnValueChangedListener);
    }

    private void bindTarget() {
        sourceOnValueChangedListener = new Property.OnValueChangedListener() {
            @Override
            public void onValueChanged(@NonNull Property sender) {
                updateTarget();
            }
        };
        source.addOnValueChangedListener(sourceOnValueChangedListener);
    }

    private void unbindSource() {
        if (targetOnValueChangedListener != null) {
            target.removeOnValueChangedListener(targetOnValueChangedListener);
        }
    }

    private void unbindTarget() {
        if (sourceOnValueChangedListener != null) {
            source.removeOnValueChangedListener(sourceOnValueChangedListener);
        }
    }
}
