package com.lakeel.altla.android.property;

import org.junit.Test;

import android.support.annotation.NonNull;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class LongPropertyTest {

    @Test
    public void constructor() {
        final LongProperty property = new LongProperty();

        assertEquals(0L, property.get());
    }

    @Test
    public void constructorLong() {
        final long expected = 1L;
        final LongProperty property = new LongProperty(expected);

        assertEquals(expected, property.get());
    }

    @Test
    public void set() {
        final LongProperty property = new LongProperty();

        final MockOnValueChangedListener listener = new MockOnValueChangedListener();
        property.addOnValueChangedListener(listener);

        final long expected = 1L;
        property.setValue(expected);

        assertEquals(expected, property.get());
        assertTrue(listener.invoked);
    }

    @Test
    public void setSameValue() {
        final long expected = 1L;
        final LongProperty property = new LongProperty(expected);

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
