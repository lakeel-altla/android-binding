package com.lakeel.altla.android.property;

import org.junit.Test;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class AbstractPropertyTest {

    @Test
    public void addOnValueChangedListener() {
        final AbstractProperty property = new AbstractProperty() {
            @Nullable
            @Override
            public Object getValue() {
                return null;
            }

            @Override
            public void setValue(@Nullable Object value) {
            }
        };

        final MockOnValueChangedListener listener1 = new MockOnValueChangedListener();
        final MockOnValueChangedListener listener2 = new MockOnValueChangedListener();
        property.addOnValueChangedListener(listener1);
        property.addOnValueChangedListener(listener2);
        property.raiseOnValueChanged();

        assertTrue(listener1.invoked);
        assertTrue(listener2.invoked);
    }

    @Test
    public void removeOnValueChangedListener() {
        final AbstractProperty property = new AbstractProperty() {
            @Nullable
            @Override
            public Object getValue() {
                return null;
            }

            @Override
            public void setValue(@Nullable Object value) {
            }
        };

        final MockOnValueChangedListener listener1 = new MockOnValueChangedListener();
        final MockOnValueChangedListener listener2 = new MockOnValueChangedListener();
        property.addOnValueChangedListener(listener1);
        property.addOnValueChangedListener(listener2);
        property.removeOnValueChangedListener(listener1);
        property.removeOnValueChangedListener(listener2);
        property.raiseOnValueChanged();

        assertFalse(listener1.invoked);
        assertFalse(listener2.invoked);
    }

    @Test
    public void clearOnValueChangedListeners() {
        final AbstractProperty property = new AbstractProperty() {
            @Nullable
            @Override
            public Object getValue() {
                return null;
            }

            @Override
            public void setValue(@Nullable Object value) {
            }
        };

        final MockOnValueChangedListener listener1 = new MockOnValueChangedListener();
        final MockOnValueChangedListener listener2 = new MockOnValueChangedListener();
        property.addOnValueChangedListener(listener1);
        property.addOnValueChangedListener(listener2);
        property.clearOnValueChangedListeners();
        property.raiseOnValueChanged();

        assertFalse(listener1.invoked);
        assertFalse(listener2.invoked);
    }

    private final class MockOnValueChangedListener implements Property.OnValueChangedListener {

        private boolean invoked;

        @Override
        public void onValueChanged(@NonNull Property sender) {
            invoked = true;
        }
    }
}
