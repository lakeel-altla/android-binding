package com.lakeel.altla.android.property;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public final class AbstractCharPropertyTest {

    @Test
    public void getValue() {
        final MockAbstractCharProperty property = new MockAbstractCharProperty();

        final char expected = 'a';
        property.value = expected;

        assertEquals(expected, property.getValue());
        assertTrue(property.getInvoked);
    }

    @Test
    public void setValue() {
        final MockAbstractCharProperty property = new MockAbstractCharProperty();

        final Character expected = 'a';
        property.setValue(expected);

        assertTrue(property.setInvoked);
        assertEquals(expected, property.getValue());
    }

    @Test
    public void setValueNull() {
        final MockAbstractCharProperty property = new MockAbstractCharProperty();

        property.setValue(null);

        assertTrue(property.setInvoked);
        assertEquals(Character.MIN_VALUE, property.getValue());
    }

    @Test
    public void setValueThrowsClassCastException() {
        final MockAbstractCharProperty property = new MockAbstractCharProperty();

        try {
            property.setValue(new Object());
            fail();
        } catch (ClassCastException e) {
            assertFalse(property.setInvoked);
        }
    }

    private final class MockAbstractCharProperty extends AbstractCharProperty {

        private boolean getInvoked;

        private boolean setInvoked;

        private char value;

        @Override
        public char get() {
            getInvoked = true;
            return value;
        }

        @Override
        public void set(char value) {
            setInvoked = true;
            this.value = value;
        }
    }
}
