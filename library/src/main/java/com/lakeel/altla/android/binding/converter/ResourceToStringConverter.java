package com.lakeel.altla.android.binding.converter;

import com.lakeel.altla.android.binding.Converter;

import android.content.res.Resources;
import android.support.annotation.NonNull;

public final class ResourceToStringConverter implements Converter {

    private final Resources resources;

    public ResourceToStringConverter(@NonNull Resources resources) {
        this.resources = resources;
    }

    @Override
    public Object convert(Object value) {
        if (value == null) {
            return null;
        }

        int resId;
        if (value instanceof Number) {
            resId = ((Number) value).intValue();
        } else if (value instanceof String) {
            resId = Integer.parseInt((String) value);
        } else {
            throw new IllegalArgumentException(String.format("Type '%s' not supported.", value.getClass()));
        }

        return resId == 0 ? null : resources.getString(resId);
    }

    @Override
    public Object convertBack(Object value) {
        throw new UnsupportedOperationException();
    }
}
