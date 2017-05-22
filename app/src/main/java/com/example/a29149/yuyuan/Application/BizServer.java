package com.example.a29149.yuyuan.Application;

import android.content.Context;
import android.util.Log;

import com.tencent.cos.COSClient;
import com.tencent.cos.COSClientConfig;
import com.tencent.cos.common.COSEndPoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by bradyxiao on 2016/9/13.
 */
public class BizServer {

    /** cos的用户接口  */
    protected COSClient cos;

    /** cosclient 配置设置; 根据需要设置；  */
    protected COSClientConfig config;

    /** 设置园区；根据创建的cos空间时选择的园区
     * 华南园区：COSEndPoint.COS_GZ(已上线)
     * 华北园区：COSEndPoint.COS_TJ(已上线)
     * 华东园区：COSEndPoint.COS_SH
     * 此处Demo中选择了 华东园区：COSEndPoint.COS_SH用于测试*/
    protected COSEndPoint cosEndPoint;

    /** cos的appid */
    protected  String appid = "1252706879";

    /** appid的一个空间名称 */
    protected  String bucket = "foolkey";
    String peristenceId = "AKIDlU4rgPAJTXbaTDTDVuKuKh2GJDwbgPkw";

    /** 上传测试 */
    protected String srcPath;
    protected boolean isSliceUpload = false;
    protected boolean checkSha = false;

    /** 下载测试 */
    protected String downloadUrl;
    protected String savePath;

    /** 删除文件测试 */
    protected String fileId;


    /** 目录前缀测试 */
    protected String prefix;

    protected static byte[] object = new byte[0];

    public static BizServer instance;
    private List<String> listPath;

    private BizServer(Context context){
        config = new COSClientConfig();
        cosEndPoint = COSEndPoint.COS_SH;
        config.setEndPoint(cosEndPoint);
        cos = new COSClient(context,appid,config,null);
        //cos = new COSClient(context,appid,config,"qcloud_demo");
    }
    public static BizServer  getInstance(Context context){
      if(instance == null){
          synchronized(object){
              instance = new BizServer(context);
          }
      }
        return instance;
    }
    public String getBucket(){
        return bucket;
    }
    public COSClient getCOSClient(){
        return cos;
    }

    public String getSrcPath(){
        return srcPath;
    }
    public void setSrcPath(String srcPath){
        this.srcPath = srcPath;
    }
    public void setSliceUpload(boolean sliceUpload){
        this.isSliceUpload = sliceUpload;
    }
    public boolean getSliceUpload(){
        return isSliceUpload;
    }
    public void setCheckSha(boolean checkSha){
        this.checkSha = checkSha;
    }
    public boolean getCheckSha(){
        return checkSha;
    }

    public List<String> getListPath() {
        return listPath;
    }
    public void setListPath(List<String> listPath){
        this.listPath = listPath;
    }
    public String getDownloadUrl(){return downloadUrl;}
    public String getSavePath(){return savePath;}
    public void setDownloadUrl(String downloadUrl){
        this.downloadUrl = downloadUrl;
    }
    public void setSavePath(String savePath){
        this.savePath = savePath;
    }

    public String getFileId(){return fileId;}
    public void setFileId(String fileId){
        this.fileId = fileId;
    }

    public String getPrefix(){return prefix;}
    public void setPrefix(String prefix){
        this.prefix = prefix;
    }


    /**
     *对fileID进行URLEncoder编码
     */
    private String urlEncoder(String fileID){
        if(fileID == null){
            return fileID;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String[] strFiled = fileID.trim().split("/");
        int length = strFiled.length;
        for(int i = 0; i< length; i++){
            if("".equals(strFiled[i]))continue;
            stringBuilder.append("/");
            try{
                String str = URLEncoder.encode(strFiled[i], "utf-8").replace("+","%20");
                stringBuilder.append(str);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(fileID.endsWith("/")) stringBuilder.append("/");
        fileID = stringBuilder.toString();
        return fileID;
    }

    /**
     * 获取单次签名
     * @return
     */
    public String getOnceSign(){
        urlEncoder(fileId);
        String onceSign = null;
        String cgi = "http://foolkey-1252706879.cossh.myqcloud.com?" + "bucket=" +bucket + "&service=cos&expired=0&path=" + fileId;
        try {
            URL url = new URL(cgi);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream in = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line = bufferedReader.readLine();
            if(line == null)return  null;
            JSONObject json = new JSONObject(line);
            if(json.has("sign")){
                onceSign = json.getString("sign");
            }
            Log.w("XIAO","onceSign =" + onceSign);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return onceSign;
    }

    /**
     * 获取多次签名
     * @return
     */
    public String getSign(){
        String sign = null;
        String cgi = "http://foolkey-1252706879.cossh.myqcloud.com?" + "bucket=" + bucket + "&service=video";
        try {
            URL url = new URL(cgi);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream in = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line = bufferedReader.readLine();
            if(line == null)return  null;
            JSONObject json = new JSONObject(line);
            if(json.has("sign")){
                sign = json.getString("sign");
            }
            Log.w("XIAO","sign=" +sign);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sign;
    }

}
