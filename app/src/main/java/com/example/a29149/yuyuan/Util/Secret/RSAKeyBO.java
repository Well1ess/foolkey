package com.example.a29149.yuyuan.Util.Secret;

import com.example.a29149.yuyuan.Util.GlobalUtil;

import org.json.JSONObject;

import java.util.Iterator;

/**
 * 获取公私钥
 * Created by geyao on 2017/4/25.
 */
public class RSAKeyBO {

    private RSACoder rsaCoder;

    private byte[] priBytes;
    private byte[] pubBytes;


    public RSAKeyBO() {
        super();
    }


    /**
     * 把一个明文JSONObject，转变为密文的json，key不会加密，对values分别加密
     * @param jsonObject
     * @return
     */
    public static JSONObject encodeJSONObject(JSONObject jsonObject) throws Exception{
        JSONObject result = new JSONObject();
        //遍历该json的key
        Iterator iterator = jsonObject.keys();
        while (iterator.hasNext()){
            //拿到key
            String key = iterator.next().toString();
            //拿到明文
            String rawStr = jsonObject.getString(key);
            System.out.println("明文是 " + rawStr);
            //对密文进行预处理
            //加密，获取密文
            String cipherText = RSAKeyBO.encryptByPub(rawStr, GlobalUtil.getInstance().getPublicKey());
            //放置在request里
            result.put( key, cipherText );
        }
        return result;
    }


    /**
     * 私钥加密
     * @param raw 明文
     * @return base64编码的密文
     * @throws Exception
     */
    private String encryptByPri(String raw) throws Exception{
        byte[] cipherBytes = rsaCoder.encryptByPrivateKey(raw.getBytes(), priBytes);
        String cipherBase64Str = ConverterByteBase64.byte2Base64(cipherBytes);
        return cipherBase64Str;
    }
    /**
     * 公钥解密
     * @param cipherBase64Str
     * @return
     * @throws Exception
     */
    private String decrypyBase64StrByPub(String cipherBase64Str) throws Exception{
        byte[]cipherBytes = ConverterByteBase64.base642Byte(cipherBase64Str);
        byte[] clearBytes = rsaCoder.decryptByPublicKey(cipherBytes, pubBytes);
        return new String(clearBytes);
    }
    /**
     * 公钥加密
     * @param raw 明文
     * @return base64格式的String密文
     * @throws Exception
     */
    public static String encryptByPub(String raw, String pubKeyStrBase64) throws Exception{
        byte[] cipherBytes = RSACoder.encryptByPublicKey(
                raw.getBytes(), ConverterByteBase64.base642Byte(pubKeyStrBase64)
        );
        String cipherBase64Str = ConverterByteBase64.byte2Base64(cipherBytes);
        return cipherBase64Str;
    }
    /**
     * 私钥解密
     * @param cipherBase64Str base64格式的String密文
     * @return 正常明文
     * @throws Exception
     */
    public static String decrypyBase64StrByPri(String cipherBase64Str, String priKeyStrBase64) throws Exception{
        byte[] cipherBytes = ConverterByteBase64.base642Byte(cipherBase64Str);
        byte[] priKeyBytes = ConverterByteBase64.base642Byte(priKeyStrBase64);
        byte[] clearBytes = RSACoder.decryptByPrivateKey(cipherBytes, priKeyBytes);
        return new String(clearBytes);
    }


}
