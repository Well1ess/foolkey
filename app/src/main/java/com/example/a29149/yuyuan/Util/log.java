package com.example.a29149.yuyuan.Util;

import android.util.Log;

/**
 * Created by 29149 on 2017/3/8.
 */

public class log {
    public static void d(Object object, String string)
    {
        Log.d(split(object.getClass().getName()), string);
    }

    public static void d(Object object, int content)
    {
        Log.d(split(object.getClass().getName()), "Num : "+content);
    }

    public static String split(String src)
    {
        String[] result=src.split("\\.");
        return result[result.length-1];
    }
}
