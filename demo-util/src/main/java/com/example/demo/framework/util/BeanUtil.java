package com.example.demo.framework.util;

import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author JiakunXu
 */
public class BeanUtil {

    /**
     *
     * @param source
     * @param target
     * @return
     */
    public static <T> T copy(Object source, @NonNull Supplier<T> target) {
        if (source == null) {
            return null;
        }

        T object = target.get();

        BeanUtils.copyProperties(source, object);

        return object;
    }

    /**
     *
     * @param source
     * @param target
     */
    public static <T> List<T> copy(List<?> source, @NonNull Supplier<T> target) {
        if (source == null) {
            return null;
        }

        List<T> list = new ArrayList<>();

        source.forEach(s -> list.add(copy(s, target)));

        return list;
    }

}
