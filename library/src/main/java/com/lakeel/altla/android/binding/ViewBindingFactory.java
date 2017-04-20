package com.lakeel.altla.android.binding;

import com.lakeel.altla.android.binding.adapter.view.ViewCommandTargetFactory;
import com.lakeel.altla.android.binding.adapter.view.ViewPropertyAdapterFactory;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;

public final class ViewBindingFactory {

    private final ViewPropertyAdapterFactory viewPropertyAdapterFactory = new ViewPropertyAdapterFactory();

    private final ViewCommandTargetFactory viewCommandTargetFactory = new ViewCommandTargetFactory();

    private final ViewResolver viewResolver;

    public ViewBindingFactory(@NonNull ViewResolver viewResolver) {
        this.viewResolver = viewResolver;
    }

    public ViewBindingFactory(@NonNull Activity activity) {
        this(new ActivityViewResolver(activity));
    }

    public ViewBindingFactory(@NonNull View view) {
        this(new DefaultViewResolver(view));
    }

    @NonNull
    public PropertyBinding create(@NonNull View view, @NonNull String propertyName, @NonNull Property source) {
        Property target = viewPropertyAdapterFactory.create(view, propertyName);
        return new PropertyBinding(target, source);
    }

    @NonNull
    public PropertyBinding create(@IdRes int id, @NonNull String propertyName, @NonNull Property source) {
        View view = resolveView(id);
        return create(view, propertyName, source);
    }

    @NonNull
    public CommandBinding create(@NonNull View view, @NonNull String commandName, @NonNull Command command) {
        CommandTarget commandTarget = viewCommandTargetFactory.create(view, commandName);
        return new CommandBinding(commandTarget, command);
    }

    @NonNull
    public CommandBinding create(@IdRes int id, @NonNull String commandName, @NonNull Command command) {
        View view = resolveView(id);
        return create(view, commandName, command);
    }

    @NonNull
    private View resolveView(@IdRes int id) {
        View view = viewResolver.resolve(id);
        if (view == null) {
            throw new IllegalArgumentException("View not found.");
        }
        return view;
    }
}
