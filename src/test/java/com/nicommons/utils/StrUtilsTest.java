package com.nicommons.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * 字符串工具测试类
 *
 * @author feng
 * @date 2018-08-13 21:48
 **/
public class StrUtilsTest {

    @Test
    public void leftCompleteTest(){
        String str = "2222";
        Assert.assertEquals("", StrUtils.leftComplete("", 2, '0'));
        Assert.assertEquals("2222", StrUtils.leftComplete(str, 2, '0'));
        Assert.assertEquals("2222", StrUtils.leftComplete(str, 4, '0'));
        Assert.assertEquals("00002222", StrUtils.leftComplete(str, 8, '0'));
    }

    @Test
    public void rightCompleteTest(){
        String str = "2222";
        Assert.assertEquals("", StrUtils.rightComplete("", 2, '0'));
        Assert.assertEquals("2222", StrUtils.rightComplete(str, 2, '0'));
        Assert.assertEquals("2222", StrUtils.rightComplete(str, 4, '0'));
        Assert.assertEquals("22220000", StrUtils.rightComplete(str, 8, '0'));
    }

    @Test
    public void midCompleteTest(){
        String str = "2222";
        Assert.assertEquals("", StrUtils.midComplete("", 2, 4,'0'));
        Assert.assertEquals("2222", StrUtils.midComplete(str, 2, -1,'0'));
        Assert.assertEquals("222200", StrUtils.midComplete(str, 6, 5,'0'));
        Assert.assertEquals("2222", StrUtils.midComplete(str, 7, 6,'0'));
        Assert.assertEquals("2220002", StrUtils.midComplete(str, 7, 4,'0'));
        Assert.assertEquals("2000222", StrUtils.midComplete(str, 7, 2,'0'));
        Assert.assertEquals("02222", StrUtils.midComplete(str, 5, 1,'0'));
    }
}
