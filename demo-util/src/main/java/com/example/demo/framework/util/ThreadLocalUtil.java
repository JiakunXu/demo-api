package com.example.demo.framework.util;

/**
 * @author JiakunXu
 */
public class ThreadLocalUtil {

    private static final ThreadLocal<Object> THREAD_LOCAL = new ThreadLocal<>();

    public static void setValue(Object value) {
        THREAD_LOCAL.set(value);
    }

    public static Object getValue() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

}
