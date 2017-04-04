package com.lakeel.altla.android.binding;

import android.support.annotation.Nullable;

public final class RelayConverter implements Converter {

    private final ConvertDelegate convertDelegate;

    private final ConvertBackDelegate convertBackDelegate;

    public RelayConverter(@Nullable ConvertDelegate convertDelegate,
                          @Nullable ConvertBackDelegate convertBackDelegate) {
        this.convertDelegate = convertDelegate;
        this.convertBackDelegate = convertBackDelegate;
    }

    @Override
    public Object convert(Object value) {
        return convertDelegate == null ? value : convertDelegate.convert(value);
    }

    @Override
    public Object convertBack(Object value) {
        return convertBackDelegate == null ? value : convertBackDelegate.convertBack(value);
    }

    public interface ConvertDelegate {

        Object convert(Object value);
    }

    public interface ConvertBackDelegate {

        Object convertBack(Object value);
    }
}
