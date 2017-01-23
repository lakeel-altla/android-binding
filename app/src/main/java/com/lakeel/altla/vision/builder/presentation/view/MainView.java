package com.lakeel.altla.vision.builder.presentation.view;

import com.google.atap.tango.ux.TangoUx;

import com.lakeel.altla.vision.builder.presentation.model.Axis;
import com.lakeel.altla.vision.builder.presentation.model.MainDebugModel;

import org.rajawali3d.renderer.ISurfaceRenderer;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

/**
 * Defines the main view.
 */
public interface MainView {

    void setTangoUxLayout(TangoUx tangoUx);

    void setSurfaceRenderer(ISurfaceRenderer renderer);

    void setTextureModelPaneVisible(boolean visible);

    void updateTextureModelPane();

    void setObjectMenuVisible(boolean visible);

    void setTranslateObjectSelected(boolean selected);

    void setTranslateObjectMenuVisible(boolean visible);

    void setTranslateObjectAxisSelected(Axis axis, boolean selected);

    void setRotateObjectSelected(boolean selected);

    void setRotateObjectMenuVisible(boolean visible);

    void setRotateObjectAxisSelected(Axis axis, boolean selected);

    void setScaleObjectSelected(boolean selected);

    void showEditTextureFragment(@Nullable String id);

    void updateDebugModel(MainDebugModel model);

    void showSnackbar(@StringRes int resId);
}
