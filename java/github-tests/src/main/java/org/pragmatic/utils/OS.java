package org.pragmatic.utils;

public class OS {
    public static String getEnvironmentVariable(String name) {
        return getEnvironmentVariable(name, null);
    }

    public static String getEnvironmentVariable(String name, String defaultValue) {
        String value = System.getenv(name);
        if (value != null) {
            return value;
        } else {
            return defaultValue;
        }
    }
}
