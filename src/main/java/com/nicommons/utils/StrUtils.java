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

    /**
     * 将字符串在左边补上对应字符
     * @param orginal 原始字符串
     * @param len 补齐后的长度
     * @param ch 用于补齐字符串的字符
     * @return 补齐后的字符串
     */
    public static String leftComplete(String orginal, int len, char ch){
        if (orginal.length() >= len || isEmity(orginal)){
            return orginal;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len - orginal.length(); i++){
            builder.append(ch);
        }
        builder.append(orginal);
        return builder.toString();
    }

    /**
     * 将字符串在右边补上对应字符
     * @param orginal 原始字符串
     * @param len 补齐后的长度
     * @param ch 用于补齐字符串的字符
     * @return 补齐后的字符串
     */
    public static String rightComplete(String orginal, int len, char ch){
        if (orginal.length() >= len || isEmity(orginal)){
            return orginal;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(orginal);
        for (int i = 0; i < len - orginal.length(); i++){
            builder.append(ch);
        }
        return builder.toString();
    }

    /**
     * 将字符串在中间指定位置补上对应字符
     * @param orginal 原始字符串
     * @param len 补齐后的长度
     * @param begin 开始插入补齐字符的位置
     * @param ch 用于补齐字符串的字符
     * @return 补齐后的字符串
     */
    public static String midComplete(String orginal, int len, int begin, char ch){
        begin--;
        int orgLen = orginal.length();
        if (orgLen >= len || isEmity(orginal)
                || begin > orgLen || begin < 0){
            return orginal;
        }

        if (begin == 0){
            return leftComplete(orginal, len, ch);
        }else if (begin == orginal.length()){
            return rightComplete(orginal, len, ch);
        }

        StringBuilder builder = new StringBuilder();

        builder.append(orginal.substring(0, begin));
        for (int i = 0; i < len - orgLen; i++){
            builder.append(ch);
        }
        builder.append(orginal.substring(begin));

        return builder.toString();
    }
}
