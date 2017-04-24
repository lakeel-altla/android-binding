package com.lakeel.altla.android.property;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public final class AbstractLongPropertyTest {

    @Test
    public void getValue() {
        final MockAbstractLongProperty property = new MockAbstractLongProperty();

        final long expected = 1L;
        property.value = expected;

        assertEquals(expected, property.getValue());
        assertTrue(property.getInvoked);
    }

    @Test
    public void setValue() {
        final MockAbstractLongProperty property = new MockAbstractLongProperty();

        final Long expected = 1L;
        property.setValue(expected);

        assertTrue(property.setInvoked);
        assertEquals(expected, property.getValue());
    }

    @Test
    public void setValueNull() {
        final MockAbstractLongProperty property = new MockAbstractLongProperty();

        property.setValue(null);

        assertTrue(property.setInvoked);
        assertEquals(0L, property.getValue());
    }

    @Test
    public void setValueThrowsClassCastException() {
        final MockAbstractLongProperty property = new MockAbstractLongProperty();

        try {
            property.setValue(new Object());
            fail();
        } catch (ClassCastException e) {
            assertFalse(property.setInvoked);
        }
    }

    private final class MockAbstractLongProperty extends AbstractLongProperty {

        private boolean getInvoked;

        private boolean setInvoked;

        private long value;

        @Override
        public long get() {
            getInvoked = true;
            return value;
        }

        @Override
        public void set(long value) {
            setInvoked = true;
            this.value = value;
        }
    }
}
