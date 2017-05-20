package com.example.resource.util;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by 张丽华 on 2017/5/18.
 * Description:
 */

public class AsyncHttpClient {

    private OnHttpResultListener onHttpResultListener;
    private OnResultExceptionListener onResultExceptionListener;
    private int timeOut = 5 * 1000;

    private Context mContext;
    private String url;
    private HttpMethod method = HttpMethod.POST;

    public AsyncHttpClient(Context context, String httpUrl, HttpMethod method) {
        mContext = context;
        url = httpUrl;
        this.method = method;
    }

    public AsyncHttpClient setOnHttpResultListener(OnHttpResultListener onHttpResultListener) {
        this.onHttpResultListener = onHttpResultListener;
        return this;
    }

    public AsyncHttpClient setOnResultExceptionListener(OnResultExceptionListener onResultExceptionListener) {
        this.onResultExceptionListener = onResultExceptionListener;
        return this;
    }

    public AsyncHttpClient setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public void send()
    {
        new HttpRequest().execute(url);
    }


    private class HttpRequest extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

//            JSONObject jsonObject =
            StringBuffer sb = new StringBuffer();
            BufferedReader reader = null;
            HttpURLConnection con = null;

            try {

                if (params.length != 1)
                    throw new IllegalArgumentException("URL 不能为空");

                java.net.URL url = new java.net.URL(params[0]);
                con = (HttpURLConnection) url.openConnection();

                Log.d("AsyncTask", params[0]);

                // 设置允许输出，默认为false
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setConnectTimeout(timeOut);
                con.setReadTimeout(timeOut);

                con.setRequestMethod(method.toString());
                con.setRequestProperty("contentType", "UTF-8");

                // 获得服务端的返回数据
                InputStreamReader read = new InputStreamReader(con.getInputStream());
                reader = new BufferedReader(read);
                String line = "";
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (Exception e) {
                if (onResultExceptionListener != null)
                    onResultExceptionListener.onException(e);
                else
                    e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        if (onResultExceptionListener != null)
                            onResultExceptionListener.onException(e);
                        else
                            e.printStackTrace();
                    }
                }
                if (con != null) {
                    con.disconnect();
                }
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.d("AsyncTask", result);

            if (result != null) {
                try {

                    if (onHttpResultListener == null)
                        new IllegalArgumentException("监听事件不能为空！");

                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");
                    if (resultFlag.equals("success")) {
                        onHttpResultListener.onSuccess(result);
                    }
                    else
                    {
                        onHttpResultListener.onFailure();
                    }
                } catch (Exception e) {
                    if (onResultExceptionListener != null)
                        onResultExceptionListener.onException(e);
                    else
                        e.printStackTrace();
                }
            } else {

                if (onResultExceptionListener != null)
                    onResultExceptionListener.onException(null);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {


        }
    }

    public interface OnResultExceptionListener {
        void onException(@Nullable Exception e);
    }

    public interface OnHttpResultListener {
        void onSuccess(String result);

        void onFailure();
    }

    public interface OnProgressUpdate {
    }
}
