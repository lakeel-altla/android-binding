package com.lakeel.altla.android.binding;

import android.support.annotation.NonNull;

public final class PropertyNameResolver {

    private static final String GET_PREFIX = "get";

    private static final String SET_PREFIX = "set";

    private static final String IS_PREFIX = "is";

    private PropertyNameResolver() {
    }

    @NonNull
    public static String resolveReadMethodName(@NonNull String propertyName, @NonNull Class<?> propertyType) {
        return resolveReadMethodPrefix(propertyType) + capitalize(propertyName);
    }

    @NonNull
    public static String resolveWriteMethodName(@NonNull String propertyName) {
        return SET_PREFIX + capitalize(propertyName);
    }

    @NonNull
    public static String resolveReadMethodNameByNonBoolean(@NonNull String propertyName) {
        return GET_PREFIX + capitalize(propertyName);
    }

    @NonNull
    public static String resolveReadMethodNameByBoolean(@NonNull String propertyName) {
        return IS_PREFIX + capitalize(propertyName);
    }

    @NonNull
    private static String resolveReadMethodPrefix(@NonNull Class<?> propertyType) {
        return (propertyType == boolean.class || propertyType == Boolean.class) ? IS_PREFIX : SET_PREFIX;
    }

    @NonNull
    private static String capitalize(@NonNull String value) {
        if (value.length() == 0) {
            return value;
        }

        char[] chars = value.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }
}
