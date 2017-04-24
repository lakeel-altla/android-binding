package com.lakeel.altla.android.property;

import org.junit.Test;

import android.support.annotation.NonNull;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public final class BooleanPropertyTest {

    @Test
    public void constructor() {
        final BooleanProperty property = new BooleanProperty();

        assertFalse(property.get());
    }

    @Test
    public void constructorBoolean() {
        final boolean expected = true;
        final BooleanProperty property = new BooleanProperty(expected);

        assertEquals(expected, property.get());
    }

    @Test
    public void set() {
        final BooleanProperty property = new BooleanProperty();

        final MockOnValueChangedListener listener = new MockOnValueChangedListener();
        property.addOnValueChangedListener(listener);

        final boolean expected = true;
        property.set(expected);

        assertEquals(expected, property.get());
        assertTrue(listener.invoked);
    }

    @Test
    public void setSameValue() {
        final boolean expected = true;
        final BooleanProperty property = new BooleanProperty(expected);

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
