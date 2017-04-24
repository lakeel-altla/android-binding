package com.lakeel.altla.android.property;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public final class AbstractShortPropertyTest {

    @Test
    public void getValue() {
        final MockAbstractShortProperty property = new MockAbstractShortProperty();

        final short expected = 1;
        property.value = expected;

        assertEquals(expected, property.getValue());
        assertTrue(property.getInvoked);
    }

    @Test
    public void setValue() {
        final MockAbstractShortProperty property = new MockAbstractShortProperty();

        final Short expected = 1;
        property.setValue(expected);

        assertTrue(property.setInvoked);
        assertEquals(expected, property.getValue());
    }

    @Test
    public void setValueNull() {
        final MockAbstractShortProperty property = new MockAbstractShortProperty();

        property.setValue(null);

        assertTrue(property.setInvoked);
        assertEquals((short) 0, property.getValue());
    }

    @Test
    public void setValueThrowsClassCastException() {
        final MockAbstractShortProperty property = new MockAbstractShortProperty();

        try {
            property.setValue(new Object());
            fail();
        } catch (ClassCastException e) {
            assertFalse(property.setInvoked);
        }
    }

    private final class MockAbstractShortProperty extends AbstractShortProperty {

        private boolean getInvoked;

        private boolean setInvoked;

        private short value;

        @Override
        public short get() {
            getInvoked = true;
            return value;
        }

        @Override
        public void set(short value) {
            setInvoked = true;
            this.value = value;
        }
    }
}
