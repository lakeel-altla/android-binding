package com.lakeel.altla.android.property;

import org.junit.Test;

import android.support.annotation.NonNull;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class IntPropertyTest {

    @Test
    public void constructor() {
        final IntProperty property = new IntProperty();

        assertEquals(0, property.get());
    }

    @Test
    public void constructorInt() {
        final int expected = 1;
        final IntProperty property = new IntProperty(expected);

        assertEquals(expected, property.get());
    }

    @Test
    public void set() {
        final IntProperty property = new IntProperty();

        final MockOnValueChangedListener listener = new MockOnValueChangedListener();
        property.addOnValueChangedListener(listener);

        final int expected = 1;
        property.setValue(expected);

        assertEquals(expected, property.get());
        assertTrue(listener.invoked);
    }

    @Test
    public void setSameValue() {
        final int expected = 1;
        final IntProperty property = new IntProperty(expected);

        final MockOnValueChangedListener listener = new MockOnValueChangedListener();
        property.addOnValueChangedListener(listener);

        property.setValue(expected);

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
