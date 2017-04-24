package com.lakeel.altla.android.property;

import org.junit.Test;

import android.support.annotation.NonNull;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class BytePropertyTest {

    @Test
    public void constructor() {
        final ByteProperty property = new ByteProperty();

        assertEquals(0, property.get());
    }

    @Test
    public void constructorInt() {
        final byte expected = 1;
        final ByteProperty property = new ByteProperty(expected);

        assertEquals(expected, property.get());
    }

    @Test
    public void set() {
        final ByteProperty property = new ByteProperty();

        final MockOnValueChangedListener listener = new MockOnValueChangedListener();
        property.addOnValueChangedListener(listener);

        final byte expected = 1;
        property.setValue(expected);

        assertEquals(expected, property.get());
        assertTrue(listener.invoked);
    }

    @Test
    public void setSameValue() {
        final byte expected = 1;
        final ByteProperty property = new ByteProperty(expected);

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
