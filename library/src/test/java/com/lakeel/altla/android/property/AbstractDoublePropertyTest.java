package com.lakeel.altla.android.property;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public final class AbstractDoublePropertyTest {

    @Test
    public void getValue() {
        final MockAbstractDoubleProperty property = new MockAbstractDoubleProperty();

        final double expected = 1.0D;
        property.value = expected;

        assertEquals(expected, property.getValue());
        assertTrue(property.getInvoked);
    }

    @Test
    public void setValue() {
        final MockAbstractDoubleProperty property = new MockAbstractDoubleProperty();

        final Double expected = 1.0D;
        property.setValue(expected);

        assertTrue(property.setInvoked);
        assertEquals(expected, property.getValue());
    }

    @Test
    public void setValueNull() {
        final MockAbstractDoubleProperty property = new MockAbstractDoubleProperty();

        property.setValue(null);

        assertTrue(property.setInvoked);
        assertEquals(0.0D, property.getValue());
    }

    @Test
    public void setValueThrowsClassCastException() {
        final MockAbstractDoubleProperty property = new MockAbstractDoubleProperty();

        try {
            property.setValue(new Object());
            fail();
        } catch (ClassCastException e) {
            assertFalse(property.setInvoked);
        }
    }

    private final class MockAbstractDoubleProperty extends AbstractDoubleProperty {

        private boolean getInvoked;

        private boolean setInvoked;

        private double value;

        @Override
        public double get() {
            getInvoked = true;
            return value;
        }

        @Override
        public void set(double value) {
            setInvoked = true;
            this.value = value;
        }
    }
}
