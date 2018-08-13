package com.nicommons.utils;

/**
 * 字符串工具类
 *
 * @author feng
 * @date 2018-08-13 21:29
 **/
public class StrUtils {


    /**
     * 字符串是否为空
     * @param str 检测的字符串
     * @return true 为空，false 非空
     */
    public static boolean isEmity(String str){
        return str == null || str.length() == 0;
    }

    /**
     * 字符串是否非空
     * @param str 检测的字符串
     * @return true 非空，false 为空
     */
    public static boolean isNotEmity(String str){
        return str != null && str.length() != 0;
    }
}
