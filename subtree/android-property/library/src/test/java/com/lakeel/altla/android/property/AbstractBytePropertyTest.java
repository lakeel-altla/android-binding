package com.lakeel.altla.android.property;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public final class AbstractBytePropertyTest {

    @Test
    public void getValue() {
        final MockAbstractByteProperty property = new MockAbstractByteProperty();

        final byte expected = 1;
        property.value = expected;

        assertEquals(expected, property.getValue());
        assertTrue(property.getInvoked);
    }

    @Test
    public void setValue() {
        final MockAbstractByteProperty property = new MockAbstractByteProperty();

        final Byte expected = 1;
        property.setValue(expected);

        assertTrue(property.setInvoked);
        assertEquals(expected, property.getValue());
    }

    @Test
    public void setValueNull() {
        final MockAbstractByteProperty property = new MockAbstractByteProperty();

        property.setValue(null);

        assertTrue(property.setInvoked);
        assertEquals((byte) 0, property.getValue());
    }

    @Test
    public void setValueThrowsClassCastException() {
        final MockAbstractByteProperty property = new MockAbstractByteProperty();

        try {
            property.setValue(new Object());
            fail();
        } catch (ClassCastException e) {
            assertFalse(property.setInvoked);
        }
    }

    private final class MockAbstractByteProperty extends AbstractByteProperty {

        private boolean getInvoked;

        private boolean setInvoked;

        private byte value;

        @Override
        public byte get() {
            getInvoked = true;
            return value;
        }

        @Override
        public void set(byte value) {
            setInvoked = true;
            this.value = value;
        }
    }
}
