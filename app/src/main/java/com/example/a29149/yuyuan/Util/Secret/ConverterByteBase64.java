package com.example.a29149.yuyuan.Util.Secret;

import android.util.Base64;

import java.io.IOException;

/**
 * Created by 张丽华 on 2017/4/27.
 * Description:
 */

public class ConverterByteBase64 {

    public static String byte2Base64(byte[] bytes){
        return  Base64.encodeToString(bytes,Base64.DEFAULT);
    }

    public static  byte[] base642Byte(String base64) throws IOException {
        return Base64.decode(base64, Base64.DEFAULT);
    }
}
