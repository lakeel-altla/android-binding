package com.lakeel.altla.android.binding;

public interface Converter {

    Object convert(Object value);

    Object convertBack(Object value);

    Converter EMPTY = new Converter() {
        @Override
        public Object convert(Object value) {
            return value;
        }

        @Override
        public Object convertBack(Object value) {
            return value;
        }
    };
}
