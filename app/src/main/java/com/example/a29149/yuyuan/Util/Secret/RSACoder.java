package com.example.a29149.yuyuan.Util.Secret;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * 非对称加密算法RSA算法组件
 * Created by geyao on 2017/4/24.
 */
public class RSACoder {
    //非对称密钥算法
    public static final String KEY_ALGORITHM="RSA";


    /**
     * 密钥长度必须是64的倍数，在512到65536位之间，越大则支持加密的长度越大
     * */
    private static final int KEY_SIZE = 1024;
    //公钥
    private static final String PUBLIC_KEY="RSAPublicKey";

    //私钥
    private static final String PRIVATE_KEY="RSAPrivateKey";

    private Map<String, Object> keyPair;


    /**
     * 初始化密钥对
     * @return Map 甲方密钥的Map
     * */
    public RSACoder(){

        try {
            KeyPairGenerator keyPairGenerator;
            //实例化密钥生成器
            keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);

            //初始化密钥生成器
            keyPairGenerator.initialize(KEY_SIZE);
            //生成密钥对
            KeyPair keyPair=keyPairGenerator.generateKeyPair();
            //甲方公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            //甲方私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            //将密钥存储在map中
            Map<String,Object> keyMap = new HashMap<String,Object>();
            keyMap.put(PUBLIC_KEY, publicKey);
            keyMap.put(PRIVATE_KEY, privateKey);
            this.keyPair = keyMap;

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 私钥加密
     * @param
     * @return 加密数据
     * */
    public byte[] encryptByPrivateKey(byte[] data,byte[] key) throws Exception{

        //取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }
    /**
     * 公钥加密
     * @param
     * @return 加密数据
     * */
    public static byte[] encryptByPublicKey(byte[] data,byte[] key) throws Exception{

        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        //产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

        //数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }


    /**
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data,byte[] key) throws Exception{
        //取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }
    /**
     * 公钥解密
     * @param
     * @param
     * @return byte[] 解密数据
     * */
    public byte[] decryptByPublicKey(byte[] data,byte[] key) throws Exception{

        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        //产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        //数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }
    /**
     * 取得私钥
     * @return byte[] 私钥
     * */
    public byte[] getPrivateKey(){

        Key key = (Key)this.keyPair.get(PRIVATE_KEY);
        return key.getEncoded();
    }
    /**
     * 取得公钥
     * @return byte[] 公钥
     * */
    public byte[] getPublicKey() throws Exception{
        Key key = (Key) this.keyPair.get(PUBLIC_KEY);
        return key.getEncoded();
    }
    /**
     * @param args
     * @throws Exception
     *
    public static void main(String[] args) throws Exception {
        //初始化密钥
        //生成密钥对
        Map<String,Object> keyMap=RSACoder.initKey();
        //公钥
        byte[] publicKey=RSACoder.getPublicKey(keyMap);

//        publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIhc9tCPkT9PlOvchhRjVBYBRPcDIploa7E2ND/N0HO7yhfyPYTpXjgTEFFx9DQX9RQ1LrKJt/vBs5/Ibf3osekCAwEAAQ==".getBytes();
        //私钥
        byte[] privateKey=RSACoder.getPrivateKey(keyMap);
//        privateKey = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAiFz20I+RP0+U69yGFGNUFgFE9wMimWhrsTY0P83Qc7vKF/I9hOleOBMQUXH0NBf1FDUusom3+8Gzn8ht/eix6QIDAQABAkAVTejltrhrmsRagS6VettFQNeEfAHo086Xdi99lGsv2XdDKQkPHPwTfFOpHeYK8fx7StsGa2+P8Zd+EPVSlee9AiEA9+fPGY5hCCqj8IZOvoVMlKyPdg35MO3Yg7YVqjqOTXMCIQCM0MumxabVmmmrhWRJfRa/jqSJyAgQjuttLWssSOKsMwIhAKsdAkItmKQ+anI3AltXPwjQIyXldz9irLQdFs8tI9QpAiAMDLHgi3lDPftc2gVC5JlVuvcJczx9bj1nEkPhfjsnUQIhALplYx2YpTwSz84fVpc2XHhNB74WMGIliXxKfZarzm4Z".getBytes();
        System.out.println("公钥：\n"+
                Base64.encodeBase64String(publicKey)
//                publicKey.toString() 无法打印

        );
        System.out.println("私钥：\n"+
                Base64.encodeBase64String(privateKey)
//                privateKey.toString() 无法打印
        );
        System.out.println();
        for (byte i : privateKey){
            System.out.print(i);
        }


        System.out.println("================密钥对构造完毕,甲方将公钥公布给乙方，开始进行加密数据的传输=============");
        String str="RSA密码交换算法";
        System.out.println("/n===========甲方向乙方发送加密数据==============");
        System.out.println("原文:"+str);
        //甲方进行数据的加密
        byte[] code1=RSACoder.encryptByPrivateKey(str.getBytes(), privateKey);
        System.out.println("加密后的数据："+Base64.encodeBase64String(code1));
        System.out.println("===========乙方使用甲方提供的公钥对数据进行解密==============");
        //乙方进行数据的解密
        byte[] decode1=RSACoder.decryptByPublicKey(code1, publicKey);
        System.out.println("乙方解密后的数据："+new String(decode1)+"\n\n");





        System.out.println("===========反向进行操作，乙方向甲方发送数据==============\n\n");

        str="乙方向甲方发送数据RSA算法";

        System.out.println("原文:"+str);

        //乙方使用公钥对数据进行加密
        byte[] code2=RSACoder.encryptByPublicKey(str.getBytes(), publicKey);
        System.out.println("===========乙方使用公钥对数据进行加密==============");
        System.out.println("加密后的数据："+Base64.encodeBase64String(code2));

        System.out.println("=============乙方将数据传送给甲方======================");
        System.out.println("===========甲方使用私钥对数据进行解密==============");

        //甲方使用私钥对数据进行解密
        byte[] decode2=RSACoder.decryptByPrivateKey(code2, privateKey);

        System.out.println("甲方解密后的数据："+new String(decode2));
    }
    */
}
