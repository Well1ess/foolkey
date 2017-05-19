package com.example.a29149.yuyuan.Util;

import android.support.annotation.NonNull;

import com.example.a29149.yuyuan.Util.Secret.AESOperator;
import com.example.a29149.yuyuan.Util.Secret.RSAKeyBO;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * 用来做发送这一步的处理
 * 转换为UTF-8编码格式
 * Created by geyao on 2017/5/17.
 */

public class HttpSender {

    //目前所有的参数，都主要发往这个地方
    public static final String cipherText = "clearText";

    public static final String token = "token";


    /**
     * 发送函数，一次性发送一条 key - value
     * 不包含接收数据的部分
     * @param urlStr 目标URL
     * @param param key
     * @param content value
     * @return
     * @throws IOException
     */
    private static HttpURLConnection send(String urlStr, String param,  Object content) throws IOException{
        HttpURLConnection con;

        //拼接key与value
        //转成UTF-8编码
        String date = content.toString();
        date = URLEncoder.encode(date, "UTF-8" );
        //如果再对date进行一次转码，则不会转
        date = param + "=" + date;
        System.out.println( "请求的URL是  -  " + urlStr );
        System.out.println( "发送的数据是  -  " + URLDecoder.decode( date ) );

        //开启网络连接
        java.net.URL url = new java.net.URL( urlStr );
        con = (HttpURLConnection) url.openConnection();

        // 设置允许输出，默认为false
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setConnectTimeout(5 * 1000);
        con.setReadTimeout(10 * 1000);

        con.setRequestMethod("POST");
        con.setRequestProperty("contentType", "UTF-8");

        //发送
        PrintWriter out = new PrintWriter(con.getOutputStream());
        out.print(date);
        out.flush();

        out.close();
        return con;
    }

    /**
     * 发送一个json对象，返回接收到的字符串
     * @param urlStr
     * @param content
     * @return 接收到的字符串
     * @throws IOException
     */
    public static String send(String urlStr, JSONObject content)throws IOException{
        //发送
        HttpURLConnection con = send(urlStr, cipherText, content);

        //接收
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        // 获得服务端的返回数据
        InputStreamReader read = new InputStreamReader(con.getInputStream());
        reader = new BufferedReader(read);
        String line = "";
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        //关闭链接
        reader.close();
        con.disconnect();

        String result = sb.toString();
        System.out.println( urlStr + "返回结果如下" );
        System.out.println( "返回的结果是  -  " + result );
        return result;
    }

    /**
     * AES加密传输
     * @param urlStr
     * @param content
     * @return
     * @throws Exception
     */
    @NonNull
    public static String sendWithAES(String urlStr, JSONObject content) throws Exception{
        return HttpSender.send( urlStr, AESOperator.getInstance().encode(content));
    }

    /**
     * 使用RSA加密传输
     * 遍历JSON对象，对每一项分别加密
     * @param urlStr
     * @param content
     * @return
     * @throws Exception
     */
    public static String sendWithRSA(String urlStr, JSONObject content) throws Exception{
        //把一个明文JSON对象转变为密文JSON
        JSONObject jsonObject = RSAKeyBO.encodeJSONObject(content);
        return send( urlStr, jsonObject );
    }
}
