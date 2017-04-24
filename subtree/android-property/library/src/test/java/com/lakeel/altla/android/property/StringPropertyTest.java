package com.lakeel.altla.android.property;

import org.junit.Test;

import android.support.annotation.NonNull;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public final class StringPropertyTest {

    @Test
    public void constructor() {
        final StringProperty property = new StringProperty();

        assertNull(property.get());
    }

    @Test
    public void constructorString() {
        final String expected = "test";
        final StringProperty property = new StringProperty(expected);

        assertSame(expected, property.get());
    }

    @Test
    public void set() {
        final StringProperty property = new StringProperty();

        final MockOnValueChangedListener listener = new MockOnValueChangedListener();
        property.addOnValueChangedListener(listener);

        final String expected = "test";
        property.set(expected);

        assertSame(expected, property.get());
        assertTrue(listener.invoked);
    }

    @Test
    public void setSameValue() {
        final String expected = "test";
        final StringProperty property = new StringProperty(expected);

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
