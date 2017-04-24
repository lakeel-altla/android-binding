package com.lakeel.altla.android.property;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public final class AbstractIntPropertyTest {

    @Test
    public void getValue() {
        final MockAbstractIntProperty property = new MockAbstractIntProperty();

        final int expected = 1;
        property.value = expected;

        assertEquals(expected, property.getValue());
        assertTrue(property.getInvoked);
    }

    @Test
    public void setValue() {
        final MockAbstractIntProperty property = new MockAbstractIntProperty();

        final Integer expected = 1;
        property.setValue(expected);

        assertTrue(property.setInvoked);
        assertEquals(expected, property.getValue());
    }

    @Test
    public void setValueNull() {
        final MockAbstractIntProperty property = new MockAbstractIntProperty();

        property.setValue(null);

        assertTrue(property.setInvoked);
        assertEquals(0, property.getValue());
    }

    @Test
    public void setValueThrowsClassCastException() {
        final MockAbstractIntProperty property = new MockAbstractIntProperty();

        try {
            property.setValue(new Object());
            fail();
        } catch (ClassCastException e) {
            assertFalse(property.setInvoked);
        }
    }

    private final class MockAbstractIntProperty extends AbstractIntProperty {

        private boolean getInvoked;

        private boolean setInvoked;

        private int value;

        @Override
        public int get() {
            getInvoked = true;
            return value;
        }

        @Override
        public void set(int value) {
            setInvoked = true;
            this.value = value;
        }
    }
}
