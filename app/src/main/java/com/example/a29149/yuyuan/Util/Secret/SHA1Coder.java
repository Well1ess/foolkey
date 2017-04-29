package com.example.a29149.yuyuan.Util.Secret;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by geyao on 2017/4/24.
 */
public class SHA1Coder {

    /**
     * @param rowString 要加密的字符串
     * @return 加密的字符串
     * SHA1加密
     */
    public final static String SHA1(String rowString) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("SHA-1");
            digest.update(rowString.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
