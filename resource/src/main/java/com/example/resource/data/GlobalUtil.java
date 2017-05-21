package com.example.resource.data;

/**
 * Created by 张丽华 on 2017/5/20.
 * Description:
 */

public class GlobalUtil {
    private static volatile GlobalUtil globalUtil;

    public static GlobalUtil getInstance()
    {
        if (globalUtil == null)
        {
            synchronized (GlobalUtil.class)
            {
                if (globalUtil == null)
                {
                    globalUtil = new GlobalUtil();
                }
            }
        }
        return globalUtil;
    }
}
