package com.lakeel.altla.android.property;

import org.junit.Test;

import android.support.annotation.NonNull;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public final class DoublePropertyTest {

    @Test
    public void constructor() {
        final DoubleProperty property = new DoubleProperty();

        assertEquals(0.0D, property.get());
    }

    @Test
    public void constructorDouble() {
        final double expected = 1.0D;
        final DoubleProperty property = new DoubleProperty(expected);

        assertEquals(expected, property.get());
    }

    @Test
    public void set() {
        final DoubleProperty property = new DoubleProperty();

        final MockOnValueChangedListener listener = new MockOnValueChangedListener();
        property.addOnValueChangedListener(listener);

        final double expected = 1.0D;
        property.set(expected);

        assertEquals(expected, property.get());
        assertTrue(listener.invoked);
    }

    @Test
    public void setSameValue() {
        final double expected = 1.0D;
        final DoubleProperty property = new DoubleProperty(expected);

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
