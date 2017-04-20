package com.lakeel.altla.android.binding;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

public final class DefaultViewResolver implements ViewResolver {

    private final View view;

    public DefaultViewResolver(@NonNull View view) {
        this.view = view;
    }

    @Nullable
    @Override
    public View resolve(@IdRes int id) {
        return view.findViewById(id);
    }
}
