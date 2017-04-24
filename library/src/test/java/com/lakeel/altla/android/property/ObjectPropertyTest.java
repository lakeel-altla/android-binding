package com.lakeel.altla.android.property;

import org.junit.Test;

import android.support.annotation.NonNull;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

public final class ObjectPropertyTest {

    @Test
    public void constructor() {
        final ObjectProperty<Object> property = new ObjectProperty<>();

        assertNull(property.get());
    }

    @Test
    public void constructorObject() {
        final Object expected = new Object();
        final ObjectProperty<Object> property = new ObjectProperty<>(expected);

        assertSame(expected, property.get());
    }

    @Test
    public void set() {
        final ObjectProperty<Object> property = new ObjectProperty<>();

        final MockOnValueChangedListener listener = new MockOnValueChangedListener();
        property.addOnValueChangedListener(listener);

        final Object expected = new Object();
        property.set(expected);

        assertSame(expected, property.get());
        assertTrue(listener.invoked);
    }

    @Test
    public void setSameValue() {
        final Object expected = new Object();
        final ObjectProperty<Object> property = new ObjectProperty<>(expected);

        final MockOnValueChangedListener listener = new MockOnValueChangedListener();
        property.addOnValueChangedListener(listener);

        property.set(expected);

        assertSame(expected, property.get());
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
