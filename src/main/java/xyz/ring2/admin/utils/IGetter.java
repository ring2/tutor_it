package xyz.ring2.admin.utils;

import java.io.Serializable;

/**
 * @author :     weiquanquan
 * @date :       2020/2/6 15:34
 * description:
 **/
@FunctionalInterface
public interface IGetter<T> extends Serializable {
    Object get(T source);
}
