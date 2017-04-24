package com.lakeel.altla.android.property;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public final class AbstractObjectPropertyTest {

    @Test
    public void getValue() {
        final MockAbstractObjectProperty property = new MockAbstractObjectProperty();

        final Object expected = new Object();
        property.value = expected;

        assertEquals(expected, property.getValue());
        assertTrue(property.getInvoked);
    }

    @Test
    public void setValue() {
        final MockAbstractObjectProperty property = new MockAbstractObjectProperty();

        final Object expected = new Object();
        property.setValue(expected);

        assertTrue(property.setInvoked);
        assertEquals(expected, property.getValue());
    }

    @Test
    public void setValueNull() {
        final MockAbstractObjectProperty property = new MockAbstractObjectProperty();

        property.setValue(null);

        assertTrue(property.setInvoked);
        assertNull(property.getValue());
    }

    @Test
    public void setValueThrowsClassCastException() {
        final IntegerAbstractObjectProperty property = new IntegerAbstractObjectProperty();

        try {
            property.setValue(Boolean.TRUE);
            fail();
        } catch (ClassCastException e) {
            assertFalse(property.setInvoked);
        }
    }

    private final class MockAbstractObjectProperty extends AbstractObjectProperty<Object> {

        private boolean getInvoked;

        private boolean setInvoked;

        private Object value;

        @Override
        public Object get() {
            getInvoked = true;
            return value;
        }

        @Override
        public void set(Object value) {
            setInvoked = true;
            this.value = value;
        }
    }

    private final class IntegerAbstractObjectProperty extends AbstractObjectProperty<Integer> {

        private boolean setInvoked;

        private Integer value;

        @Override
        public Integer get() {
            return value;
        }

        @Override
        public void set(Integer value) {
            setInvoked = true;
            this.value = value;
        }
    }
}
