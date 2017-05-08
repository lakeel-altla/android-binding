package com.lakeel.altla.android.binding.converter;

import com.lakeel.altla.android.binding.Converter;

import android.view.View;

public final class BooleanToVisibilityConverter implements Converter {

    public static final BooleanToVisibilityConverter INSTANCE = new BooleanToVisibilityConverter();

    private BooleanToVisibilityConverter() {
    }

    @Override
    public Object convert(Object value) {
        if (value == null) {
            return null;
        }

        boolean booleanValue;
        if (value instanceof Boolean) {
            booleanValue = (Boolean) value;
        } else if (value instanceof String) {
            booleanValue = Boolean.parseBoolean((String) value);
        } else {
            throw new IllegalArgumentException(String.format("Type '%s' not supported.", value.getClass()));
        }

        return booleanValue ? View.VISIBLE : View.GONE;
    }

    @Override
    public Object convertBack(Object value) {
        if (value == null) {
            return Boolean.FALSE;
        }

        if (value instanceof Integer) {
            return ((Integer) value) == View.VISIBLE;
        } else {
            throw new IllegalArgumentException(String.format("Type '%s' not supported.", value.getClass()));
        }
    }
}
