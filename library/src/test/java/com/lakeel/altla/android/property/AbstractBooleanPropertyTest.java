package com.lakeel.altla.android.property;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public final class AbstractBooleanPropertyTest {

    @Test
    public void getValue() {
        final MockAbstractBooleanProperty property = new MockAbstractBooleanProperty();

        final boolean expected = false;
        property.value = expected;

        assertEquals(expected, property.getValue());
        assertTrue(property.getInvoked);
    }

    @Test
    public void setValue() {
        final MockAbstractBooleanProperty property = new MockAbstractBooleanProperty();

        final Boolean expected = Boolean.TRUE;
        property.setValue(expected);

        assertTrue(property.setInvoked);
        assertEquals(expected, property.value);
    }

    @Test
    public void setValueNull() {
        final MockAbstractBooleanProperty property = new MockAbstractBooleanProperty();

        final boolean expected = false;
        property.setValue(null);

        assertTrue(property.setInvoked);
        assertEquals(expected, property.value);
    }

    @Test
    public void setValueThrowsClassCastException() {
        final MockAbstractBooleanProperty property = new MockAbstractBooleanProperty();

        try {
            property.setValue(new Object());
            fail();
        } catch (ClassCastException e) {
            assertFalse(property.setInvoked);
        }
    }

    private final class MockAbstractBooleanProperty extends AbstractBooleanProperty {

        private boolean getInvoked;

        private boolean setInvoked;

        private boolean value;

        @Override
        public boolean get() {
            getInvoked = true;
            return value;
        }

        @Override
        public void set(boolean value) {
            setInvoked = true;
            this.value = value;
        }
    }
}
