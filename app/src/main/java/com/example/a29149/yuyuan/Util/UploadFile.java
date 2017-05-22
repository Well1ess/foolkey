package com.example.a29149.yuyuan.Util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.Application.BizServer;
import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.PutObjectRequest;
import com.tencent.cos.model.PutObjectResult;
import com.tencent.cos.task.listener.IUploadTaskListener;
import com.tencent.cos.utils.FileUtils;

import static com.example.a29149.yuyuan.Application.MyApplication.cos;

/**
 * Created by MaLei on 2017/5/22.
 * Email:ml1995@mail.ustc.edu.cn
 * 上传文件至腾讯云服务器
 */

public class UploadFile {
    public static String bucket = "foolkey";
    public static String cosPath = "/photo/test4.png";
    //web.myqcloud.com
    public static String srcPath = "";//本地文件的绝对路径
    public static String sign = "";
    public Context mContext;
    public BizServer bizServer ;

    public UploadFile(Context mContext){
        this.mContext = mContext;
        bizServer = BizServer.getInstance(mContext);
    }
    public static void setSrcPath(String srcPath) {
        UploadFile.srcPath = srcPath;

    }

    public static PutObjectRequest putObjectRequest = new PutObjectRequest();

    public static void updata(String srcPath) {
        putObjectRequest.setBucket(bucket);
        putObjectRequest.setCosPath(cosPath);
        putObjectRequest.setSrcPath(srcPath);
        putObjectRequest.setSign(sign);

        Log.i("malei","开始上传");
        putObjectRequest.setListener(new IUploadTaskListener() {
            @Override
            public void onSuccess (COSRequest cosRequest, COSResult cosResult){

                PutObjectResult result = (PutObjectResult) cosResult;
                if (result != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(" 上传结果： ret=" + result.code + "; msg =" + result.msg + "\n");
                    stringBuilder.append(" access_url= " + result.access_url == null ? "null" : result.access_url + "\n");
                    stringBuilder.append(" resource_path= " + result.resource_path == null ? "null" : result.resource_path + "\n");
                    stringBuilder.append(" url= " + result.url == null ? "null" : result.url);
                    Log.w("TEST", stringBuilder.toString());
                }
            }

            @Override
            public void onFailed (COSRequest COSRequest,final COSResult cosResult){
                Log.w("TEST", "上传出错： ret =" + cosResult.code + "; msg =" + cosResult.msg);
            }

            @Override
            public void onProgress (COSRequest cosRequest,final long currentSize, final long totalSize){
                float progress = (float) currentSize / totalSize;
                progress = progress * 100;
                Log.w("TEST", "进度：  " + (int) progress + "%");
            }

            @Override
            public void onCancel(COSRequest cosRequest, COSResult cosResult) {

            }
        });
        PutObjectResult result = cos.putObject(putObjectRequest);
    }
    public void upload(String srcPath){
        bizServer.setFileId(cosPath);
        bizServer.setSrcPath(srcPath);
        PutObjectSamples putObjectSamples = new PutObjectSamples(new TextView(mContext),new TextView(mContext), PutObjectSamples.PUT_TYPE.SAMPLE);
        putObjectSamples.execute(bizServer);
    }
    public void update(String getCosPahtName){
        if(TextUtils.isEmpty(getCosPahtName)){
            Toast.makeText(mContext,"文件名为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!getCosPahtName.startsWith("/")){
            bizServer.setFileId("/" + getCosPahtName);
        }else{
            bizServer.setFileId(getCosPahtName);
        }

        UpdateObjectSamples updateObjectSamples = new UpdateObjectSamples(new TextView(mContext),true);
        updateObjectSamples.execute(bizServer);

    }
}
