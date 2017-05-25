package com.example.a29149.yuyuan.business_object.com;

import android.content.Intent;
import android.support.annotation.Nullable;

import com.bumptech.glide.RequestManager;

/**
 * 用来加载图片的URL
 * Created by geyao on 2017/5/22.
 */

public class PictureInfoBO extends AbstractBO {

    //下载用的链接，通过CDN加速
    public static final String objectDBUrl = "http://foolkey-1252706879.file.myqcloud.com/";

    //云端默认的图片数量
    public static final Integer defaultPicNum = 14;

    //Glide依赖
    private RequestManager glide;


    /**
     * 根据用户的userName找图片路径
     */
    public String getPhotoURL(String userName){
        String url;
        if (userName == null || userName.equals("")){
            url = objectDBUrl + "photo/test.png";
            return url;
        }
        url = getLocalPhoto(userName);
        if (url == null){
            //本地没有
            url = getOnlinePhoto(userName);
        }
        return url;
    }

    /**
     * 根据本地找寻图片缓存
     * 没有，则返回null
     * @param userName
     * @return
     */
    private String getLocalPhoto(String userName){
        return null;
    }

    /**
     * 上传头像时，约定的云端路径
     * @param userName
     * @return
     */
    public static String getOnlinePhoto(String userName){
        return   objectDBUrl + "photo/user/head/"
                + userName + ".png"
//                + "photo/test.png"
                ;
    }

    /**
     * 获取用于上传的路径
     * 有时候也用于保存在本地
     * @param userName
     * @return
     */
    public static String getUrlForUpload(String userName){
        return "photo/user/head/"
                + userName + ".png";
    }

    /**
     * 获取默认头像本地路径
     * @return
     */
    public static String getDefaultLocalPicUrl(){
        return null;
    }

    /**
     * 本地存一个文件，获取它应有的文件名
     * @param fileName
     * @return
     */
    public static String getLocalUrl(String fileName){
        return null;
    }

    /**
     * 获取云端的默认头像URL
     * @param num
     * @return
     */
    public static String getDefaultPicCloudPath(@Nullable  Integer num){
        if ( num == null || num <0 && num > defaultPicNum )
            num = 0;
        return objectDBUrl + "photo/default/" + num + ".png";
    }
}
