package com.liyanxing.util;

import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @Author xiaoliu
 * @Date 2019/7/10 14:21
 * @Description 工具类
 */
public class CommonUtils extends StringUtils {

    private static final String NULLSTR = "";



    /**
     * 判断一个对象是否为空
     * true :为空
     * false : 不为空
     *
     * @param object
     * @return
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * 判断一个对象是否是数组
     *
     * @param object
     * @return
     */
    public static boolean isArray(Object object) {
        return !isNull(object) && object.getClass().isArray();
    }

    /**
     * 字符串去空格
     *
     * @param str
     * @return
     */
    public static String trim(String str) {
        return (str == null ? "" : str.trim());
    }

    /**
     * 判断一个字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return isNull(str) || NULLSTR.equals(str);
    }

    /**
     * 判断一个字符串是否不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断map为空
     *
     * @param map
     * @return
     */
    public static boolean isEmptyMap(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * 判断map不为空
     *
     * @param map
     * @return
     */
    public static boolean isNotEmptyMap(Map<?, ?> map) {
        return !isNull(map);
    }

    /**
     * 判断数组为空
     *
     * @param objects
     * @return
     */
    public static boolean isEmptyArray(Object[] objects) {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * 判断数组不为空
     *
     * @param objects
     * @return
     */
    public static boolean isNotEmptyArray(Object[] objects) {
        return !isEmptyArray(objects);
    }

    /**
     * 判断集合为空
     *
     * @return
     */
    public static boolean isEmptyCollection(Collection<?> collection) {
        return isNull(collection) || collection.isEmpty();
    }

    /**
     * 判断集合不为空
     *
     * @param collection
     * @return
     */
    public static boolean isNotEmptyCollection(Collection<?> collection) {
        return !isEmptyCollection(collection);
    }

}
