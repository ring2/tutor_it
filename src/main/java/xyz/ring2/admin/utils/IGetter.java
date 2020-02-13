package xyz.ring2.admin.utils;

import java.io.Serializable;

/**
 * @author :     weiquanquan
 * @date :       2020/2/6 15:34
 * description:  自定义函数式接口
 **/
@FunctionalInterface
public interface IGetter<T> extends Serializable {
    Object get(T source);
}
