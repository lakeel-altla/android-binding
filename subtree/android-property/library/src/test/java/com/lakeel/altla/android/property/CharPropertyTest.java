package com.lakeel.altla.android.property;

import junit.framework.Assert;

import org.junit.Test;

import android.support.annotation.NonNull;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public final class CharPropertyTest {

    @Test
    public void constructor() {
        final CharProperty property = new CharProperty();

        Assert.assertEquals(Character.MIN_VALUE, property.get());
    }

    @Test
    public void constructorDouble() {
        final char expected = 'a';
        final CharProperty property = new CharProperty(expected);

        Assert.assertEquals(expected, property.get());
    }

    @Test
    public void set() {
        final CharProperty property = new CharProperty();

        final MockOnValueChangedListener listener = new MockOnValueChangedListener();
        property.addOnValueChangedListener(listener);

        final char expected = 'a';
        property.set(expected);

        Assert.assertEquals(expected, property.get());
        assertTrue(listener.invoked);
    }

    @Test
    public void setSameValue() {
        final char expected = 'a';
        final CharProperty property = new CharProperty(expected);

        final MockOnValueChangedListener listener = new MockOnValueChangedListener();
        property.addOnValueChangedListener(listener);

        property.set(expected);

        Assert.assertEquals(expected, property.get());
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
