package com.example.demo.framework.mapper;

import java.util.List;

/**
 * @author JiakunXu
 */
public interface BaseMapper<T> {

    T stats(T t);

    /**
     *
     * @param t
     * @return
     */
    int count(T t);

    /**
     *
     * @param t
     * @return
     */
    List<T> list(T t);

    /**
     *
     * @param t
     * @return
     */
    T get(T t);

    /**
     *
     * @param t
     * @return
     */
    int insert(T t);

    /**
     *
     * @param t
     * @return
     */
    int update(T t);

    /**
     *
     * @param t
     * @return
     */
    int delete(T t);

}
