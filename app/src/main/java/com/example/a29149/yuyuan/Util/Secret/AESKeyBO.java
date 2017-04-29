package com.example.a29149.yuyuan.Util.Secret;

/**
 * 不管健壮，追求性能
 * Created by geyao on 2017/4/26.
 */
public class AESKeyBO {


    private static final String token1 = "key";
    private static final String token2 = "aesKey";

    private AESCoder aesCoder;

    private String base64key;

    /**
     * 先从缓存中获取该用户的key，没有的话，建一个
     * @return base64编码的key
     */
    public String getKeybase64Str(String userToken) throws Exception{
        return aesCoder.getAESKeyBase64();
    }

    /**
     * 加密
     * @param RowStr 明文
     * @param base64KeyStr base64格式的密钥
     * @return base64格式的密文
     * @throws Exception
     */
    public String encrypt(String RowStr, String base64KeyStr) throws Exception{
        byte[] cipherBytes = aesCoder.encryptAES(RowStr.getBytes(),
                aesCoder.loadKeyAES(base64KeyStr)
                );
        return ConverterByteBase64.byte2Base64(cipherBytes);
    }
    /**
     * 解密
     * @param cipherBase64Str base64格式的密文
     * @param base64KeyStr base64格式的密钥
     * @return 明文
     * @throws Exception
     */
    public String decrypt(String cipherBase64Str, String base64KeyStr) throws Exception{
        byte[] cipherBytes = ConverterByteBase64.base642Byte(cipherBase64Str);
        byte[] clearBytes = aesCoder.decryptAES( cipherBytes,
                aesCoder.loadKeyAES(base64KeyStr)
                );
        return new String(clearBytes);
    }
}
