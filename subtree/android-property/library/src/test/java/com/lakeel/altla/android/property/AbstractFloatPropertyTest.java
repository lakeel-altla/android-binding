package com.lakeel.altla.android.property;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public final class AbstractFloatPropertyTest {

    @Test
    public void getValue() {
        final MockAbstractFloatProperty property = new MockAbstractFloatProperty();

        final float expected = 1.0f;
        property.value = expected;

        assertEquals(expected, property.getValue());
        assertTrue(property.getInvoked);
    }

    @Test
    public void setValue() {
        final MockAbstractFloatProperty property = new MockAbstractFloatProperty();

        final Float expected = 1.0f;
        property.setValue(expected);

        assertTrue(property.setInvoked);
        assertEquals(expected, property.getValue());
    }

    @Test
    public void setValueNull() {
        final MockAbstractFloatProperty property = new MockAbstractFloatProperty();

        property.setValue(null);

        assertTrue(property.setInvoked);
        assertEquals(0.0f, property.getValue());
    }

    @Test
    public void setValueThrowsClassCastException() {
        final MockAbstractFloatProperty property = new MockAbstractFloatProperty();

        try {
            property.setValue(new Object());
            fail();
        } catch (ClassCastException e) {
            assertFalse(property.setInvoked);
        }
    }

    private final class MockAbstractFloatProperty extends AbstractFloatProperty {

        private boolean getInvoked;

        private boolean setInvoked;

        private float value;

        @Override
        public float get() {
            getInvoked = true;
            return value;
        }

        @Override
        public void set(float value) {
            setInvoked = true;
            this.value = value;
        }
    }
}
