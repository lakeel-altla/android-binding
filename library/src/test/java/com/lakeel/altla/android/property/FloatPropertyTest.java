package com.lakeel.altla.android.property;

import org.junit.Test;

import android.support.annotation.NonNull;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public final class FloatPropertyTest {

    @Test
    public void constructor() {
        final FloatProperty property = new FloatProperty();

        assertEquals(0.0F, property.get());
    }

    @Test
    public void constructorFloat() {
        final float expected = 1.0F;
        final FloatProperty property = new FloatProperty(expected);

        assertEquals(expected, property.get());
    }

    @Test
    public void set() {
        final FloatProperty property = new FloatProperty();

        final MockOnValueChangedListener listener = new MockOnValueChangedListener();
        property.addOnValueChangedListener(listener);

        final float expected = 1.0F;
        property.set(expected);

        assertEquals(expected, property.get());
        assertTrue(listener.invoked);
    }

    @Test
    public void setSameValue() {
        final float expected = 1.0F;
        final FloatProperty property = new FloatProperty(expected);

        final MockOnValueChangedListener listener = new MockOnValueChangedListener();
        property.addOnValueChangedListener(listener);

        property.set(expected);

        assertEquals(expected, property.get());
        assertFalse(listener.invoked);
    }

    private final class MockOnValueChangedListener implements Property.OnValueChangedListener {

        private boolean invoked;

        @Override
        public void onValueChanged(@NonNull Property sender) {
            invoked = true;
        }
    }
}
