package com.example.a29149.yuyuan.Util;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

/**
 * 用来做发送这一步的处理
 * 转换为UTF-8编码格式
 * Created by geyao on 2017/5/17.
 */

public class HttpSender {

    //目前所有的参数，都主要发往这个地方
    public static final String cipherText = "cipherText";


    /**
     * 发送函数，一次性发送一条 key - value
     * @param urlStr 目标URL
     * @param param key
     * @param content value
     * @return
     * @throws IOException
     */
    public static HttpURLConnection send(String urlStr, String param,  Object content) throws IOException{
        HttpURLConnection con;

        //拼接key与value
        //转成UTF-8编码
        String date = URLEncoder.encode( param + "=" + content.toString(), "UTF-8" );
        System.out.println( "发送的数据是  -  " + date );

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
     * 默认位cipherText
     * @param urlStr
     * @param content
     * @return
     * @throws IOException
     */
    public static HttpURLConnection send(String urlStr, Object content)throws IOException{
        return send(urlStr, cipherText, content);
    }
}
