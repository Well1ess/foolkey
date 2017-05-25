package com.example.a29149.yuyuan.business_object.com;

import android.os.AsyncTask;

/**
 * 这个用来判断本地是否有默认的图片
 * 如果没有，下载
 * Created by geyao on 2017/5/25.
 */

public class DownloadDefaultImage extends AsyncTask <String, String, String>{


    /**
     * 用来检测本地是否有图片
     * @return
     */
    public static boolean checkImage(String param){
        switch (param){
            case "default":{

            }break;
            default:return false;
        }
        return false;
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
