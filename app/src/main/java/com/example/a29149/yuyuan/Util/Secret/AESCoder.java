package com.example.a29149.yuyuan.Util.Secret;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by geyao on 2017/4/26.
 */
public class AESCoder {

    private String ivParameter = "0392039203920388";//偏移量,可自行修改
    private IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());

    //对称密钥算法
    private static final String KEY_ALGORITHM="AES";
    /**
     * 密钥长度
     * */
    private static final int KEY_SIZE = 128;

    private static final String CIPHER = "AES/CBC/PKCS5Padding";

    /**
     * 生成密钥
     * @return base64格式的密钥
     * @throws Exception
     */
    public String getAESKeyBase64() throws Exception{
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
        keyGenerator.init(KEY_SIZE);
        SecretKey key = keyGenerator.generateKey();
        String base64Str = ConverterByteBase64.byte2Base64(key.getEncoded());
        return  base64Str;
    }

    /**
     * 获取密钥
     * @param base64Key
     * @return
     * @throws Exception
     */
    public SecretKey loadKeyAES(String base64Key) throws Exception{
        byte[] bytes = ConverterByteBase64.base642Byte(base64Key);
        SecretKey key = new SecretKeySpec(bytes, KEY_ALGORITHM);
        return key;
    }

    /**
     * 加密
     * @param source 待加密的二进制数组，可以由 明文Str.getBytes()得到
     * @param secretKey 对称密钥
     * @return 加密后的二进制数组
     * @throws Exception
     */
    public byte[] encryptAES(byte[] source, SecretKey secretKey) throws Exception{
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(CIPHER);// 创建密码器
        byte[] byteContent = source;
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);// 初始化
        byte[] result = cipher.doFinal(byteContent);
        return result; // 加密
    }

    /**
     * 解密
     * @param source 待解密的二进制数组
     * @param secretKey 对称密钥
     * @return 解密后的二进制数组，可以使用new String(result)得到明文
     * @throws Exception
     */
    public byte[] decryptAES(byte[] source, SecretKey secretKey) throws Exception{
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(CIPHER);// 创建密码器
        cipher.init(Cipher.DECRYPT_MODE, key, iv);// 初始化
        byte[] result = cipher.doFinal(source);
        return result; // 加密
    }

    private static int getRandom(int count) {
        return (int) Math.round(Math.random() * (count));
    }

    private static String string = "abcdefghijklmnopqrstuvwxyz1234567890";

    public static String getRandomString(int length){
        StringBuffer sb = new StringBuffer();
        int len = string.length();
        for (int i = 0; i < length; i++) {
            sb.append(string.charAt(getRandom(len - 1)));
        }
        return sb.toString();
    }
}
