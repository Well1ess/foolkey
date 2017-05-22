package com.example.a29149.yuyuan.Util;

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.a29149.yuyuan.Application.BizServer;
import com.tencent.cos.common.COSAuthority;
import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.UpdateObjectRequest;
import com.tencent.cos.task.listener.ICmdTaskListener;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by bradyxiao on 2016/10/13.
 */
public class UpdateObjectSamples extends AsyncTask<BizServer,Void,String>{
    protected String resultStr;
    protected TextView detailText;
    protected  boolean isUpdateForFile = true;
    public UpdateObjectSamples(TextView detailText, boolean isUpdateForFile){
        this.detailText = detailText;
        this.isUpdateForFile = isUpdateForFile;
    }
    @Override
    protected String doInBackground(BizServer... params) {

        Map<String, String> customer = new LinkedHashMap<String, String>();
        customer.put("Cache-Control","no-cache");
        customer.put("Content-Disposition","attachment");
        customer.put("Content-Language","content=\"zh-cn\"");
        customer.put("x-cos-meta-","自定义属性");
        String biz_attr = "文件属性更新";
        String authority = COSAuthority.EINVALID;
        /** UpdateObjectRequest 请求对象 */
        UpdateObjectRequest updateObjectRequest = new UpdateObjectRequest();
        /** 设置Bucket */
        updateObjectRequest.setBucket(params[0].getBucket());
        /** 设置cosPath :远程路径*/
        updateObjectRequest.setCosPath(params[0].getFileId());
        /** 设置sign: 签名，此处使用单次签名 */
        updateObjectRequest.setSign(params[0].getOnceSign());
        /** biz_attr: 更新属性 */
        updateObjectRequest.setBizAttr(biz_attr);
        if(isUpdateForFile){
            /** biz_attr: 更新头部属性*/
            updateObjectRequest.setCustomHeader(customer);
            /** biz_attr: 更新权限属性 */
            updateObjectRequest.setAuthority(authority);
        }
        /** 设置listener: 结果回调 */
        updateObjectRequest.setListener(new ICmdTaskListener() {
            @Override
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
                resultStr = cosResult.code+" : "+cosResult.msg;
            }

            @Override
            public void onFailed(COSRequest cosRequest, COSResult cosResult) {
                resultStr = cosResult.code+" : "+cosResult.msg;
            }
        });
        /** 发送请求：执行 */
        params[0].getCOSClient().updateObject(updateObjectRequest);
        return resultStr;
    }
    @Override
    protected void onPostExecute(String s) {
        detailText.setText(s);
    }
}
