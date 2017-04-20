package com.lakeel.altla.android.binding;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

public final class ActivityViewResolver implements ViewResolver {

    private final Activity activity;

    public ActivityViewResolver(@NonNull Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View resolve(@IdRes int id) {
        return activity.findViewById(id);
    }
}
