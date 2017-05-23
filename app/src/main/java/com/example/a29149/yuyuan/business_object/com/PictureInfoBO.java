package com.example.a29149.yuyuan.business_object.com;

import com.bumptech.glide.RequestManager;

/**
 * 用来加载图片的URL
 * Created by geyao on 2017/5/22.
 */

public class PictureInfoBO extends AbstractBO {

    public static final String objectDBUrl = "http://foolkey-1252706879.cossh.myqcloud.com/";

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

    public static String getOnlinePhoto(String userName){
        return    "photo/user/head/"
                + userName + ".png"
//                + "photo/test.png"
                ;
    }

    public static String getUrlForUpload(String userName){
        return "photo/user/head/"
                + userName + ".png";
    }
}
