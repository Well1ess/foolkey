package com.example.a29149.yuyuan.Util;

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.a29149.yuyuan.Application.BizServer;
import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.PutObjectRequest;
import com.tencent.cos.model.PutObjectResult;
import com.tencent.cos.task.listener.IUploadTaskListener;
import com.tencent.cos.utils.FileUtils;

import java.io.File;
import java.util.List;

/**
 * Created by bradyxiao on 2016/10/13.
 */
public class PutObjectSamples extends AsyncTask<BizServer,Long,String>{
    private String resultStr;
    private TextView detailText;
    private TextView progressText;
    private IUploadListenerHandler iUploadListenerHandler;
    private PUT_TYPE put_type;
    public  enum PUT_TYPE{
        SAMPLE(1,"简单文件上传模式"),
        SLICE(2,"分片文件上传模式"),
        LIST(3,"一键文件上传模式");
        private int id;
        private String desc;
        PUT_TYPE(int id, String desc){
            this.id = id;
            this.desc = desc;
        }
        public String getDesc(){
            return desc;
        }
    }
    public PutObjectSamples(TextView detailText, TextView progressText, PUT_TYPE put_type){
        this.detailText = detailText;
        this.progressText = progressText;
        iUploadListenerHandler = new IUploadListenerHandler();
        this.put_type = put_type;
    }
    @Override
    protected String doInBackground(BizServer... bizServers) {
        switch (put_type){
            case SAMPLE:
                putObjcetForSmallFile(bizServers[0]);
                break;
            case SLICE:
                putObjcetForLargeFile(bizServers[0]);
                break;
            case LIST:
                putObjcetForListFile(bizServers[0]);
                break;
        }
        return resultStr;
    }

    @Override
    protected void onPostExecute(String s) {
        detailText.setText(s);
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        progressText.setText("下载进度 :" + values[0].intValue() + "%");
    }


    /**
     * 简单文件上传
     */
    protected void putObjcetForSmallFile(BizServer bizServer){
        /** PutObjectRequest 请求对象 */
        PutObjectRequest putObjectRequest = new PutObjectRequest();
        /** 设置Bucket */
        putObjectRequest.setBucket(bizServer.getBucket());
        /** 设置cosPath :远程路径*/
        putObjectRequest.setCosPath(bizServer.getFileId());
        /** 设置srcPath: 本地文件的路径 */
        putObjectRequest.setSrcPath(bizServer.getSrcPath());
        /** 设置 insertOnly: 是否上传覆盖同名文件*/
        putObjectRequest.setInsertOnly("1");
        /** 设置sign: 签名，此处使用多次签名 */
        putObjectRequest.setSign(bizServer.getSign());

        /** 设置sha: 是否上传文件时带上sha，一般不需要带*/
        if(bizServer.getCheckSha()){
            putObjectRequest.checkSha();
            putObjectRequest.setSha(putObjectRequest.getsha());
        }
        /** 设置listener: 结果回调 */
        putObjectRequest.setListener(iUploadListenerHandler);
        /** 发送请求：执行 */
        bizServer.getCOSClient().putObject(putObjectRequest);
    }
    /**
     * 分片上传 一般文件》20M,使用分片上传;
     */
    protected void putObjcetForLargeFile(BizServer bizServer){
        PutObjectRequest putObjectRequest = new PutObjectRequest();
        putObjectRequest.setBucket(bizServer.getBucket());
        putObjectRequest.setCosPath(bizServer.getFileId());

        putObjectRequest.setSrcPath(bizServer.getSrcPath());
        /** 设置sliceFlag: 是否开启分片上传 */
        putObjectRequest.setSliceFlag(true);
        /** 设置slice_size: 若使用分片上传，设置分片的大小 */
        putObjectRequest.setSlice_size(1024*1024);

        putObjectRequest.setSign(bizServer.getSign());

        putObjectRequest.setInsertOnly("1");

        if(bizServer.getCheckSha()){
            putObjectRequest.checkSha();
            putObjectRequest.setSha(putObjectRequest.getsha());
        }

        putObjectRequest.setListener(iUploadListenerHandler);

        bizServer.getCOSClient().putObject(putObjectRequest);

    }
    /**
     * 一键上传
     */
    protected void putObjcetForListFile(BizServer bizServer){
        List<String> listPath = bizServer.getListPath();
        PutObjectRequest putObjectRequest = null;
        for(int i = 0;i < listPath.size(); i++){
            putObjectRequest = new PutObjectRequest();
            putObjectRequest.setBucket(bizServer.getBucket());
            putObjectRequest.setCosPath(bizServer.getFileId()+ File.separator + FileUtils.getFileName(listPath.get(i)));
            putObjectRequest.setSign(bizServer.getSign());
            putObjectRequest.setListener(iUploadListenerHandler);

            putObjectRequest.setSrcPath(listPath.get(i));
            bizServer.getCOSClient().putObject(putObjectRequest);
        }
    }
    protected class IUploadListenerHandler implements IUploadTaskListener {

        @Override
        public void onProgress(COSRequest cosRequest, long currentSize, long totalSize) {
            long progress = ((long) ((100.00 * currentSize) / totalSize));
            publishProgress(progress);
        }

        @Override
        public void onCancel(COSRequest cosRequest, COSResult cosResult) {

        }

        @Override
        public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
            PutObjectResult result = (PutObjectResult) cosResult;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" 上传结果： ret=" + result.code + "; msg =" +result.msg + "\n");
            stringBuilder.append(" access_url= " + result.access_url == null ? "null" :result.access_url + "\n");
            stringBuilder.append(" resource_path= " + result.resource_path == null ? "null" :result.resource_path + "\n");
            stringBuilder.append(" url= " + result.url == null ? "null" :result.url);
            resultStr = stringBuilder.toString();
        }

        @Override
        public void onFailed(COSRequest cosRequest, COSResult cosResult) {
            resultStr = "上传出错： ret =" +cosResult.code + "; msg =" + cosResult.msg;
        }
    }
}
