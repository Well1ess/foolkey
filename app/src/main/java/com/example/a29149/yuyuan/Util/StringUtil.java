package com.example.a29149.yuyuan.Util;

/**
 * 一个专门对字符串做处理的类
 * Created by geyao on 2017/6/8.
 */

public class StringUtil {

    /**
     * 用于在单行的TextView显示文字
     * @param obj 需要在UI上展示的对象
     * @param length UI上几乎最长的数量
     * @return 一个长度为 length + 3个 . 的字符串
     */
    public static String subString(Object obj, int length){
        if (obj == null)
            return "";
        String result = obj.toString();
        if (result.length() > length){
            result = result.substring(0, length) + "...";
        }
        return result;
    }
}
