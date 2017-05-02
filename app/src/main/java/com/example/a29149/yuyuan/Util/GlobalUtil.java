package com.example.a29149.yuyuan.Util;

import com.example.a29149.yuyuan.DTO.CouponDTO;
import com.example.a29149.yuyuan.DTO.CourseTeacherDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张丽华 on 2017/4/22.
 * Description:
 */

public class GlobalUtil {
    private static volatile GlobalUtil globalUtil;

    private GlobalUtil() {
    }

    public static GlobalUtil getInstance()
    {
        if (globalUtil == null)
        {
            synchronized (GlobalUtil.class)
            {
                if (globalUtil == null)
                {
                    globalUtil = new GlobalUtil();
                }
            }
        }
        return globalUtil;
    }

    //主页热门课程列表
    private List<String> content;
    //公钥
    private String publicKey;
    //ASE
    private String AESKey;
    //Token
    private String token;
    //抵扣卷的List
    private List<CouponDTO> couponDTOList;
    //课程列表
    private List<CourseTeacherDTO> courseTeacherDTOs;

    public List<String> getContent() {
        if (content == null)
        {
            content = new ArrayList<>();
            content.add("测试数据1");
            content.add("测试数据2");
            content.add("测试数据3");
            content.add("测试数据4");
            content.add("测试数据5");

        }
        return content;
    }
    public void setContent(List<String> content) {
        this.content = content;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getAESKey() {
        return AESKey;
    }

    public void setAESKey(String AESKey) {
        this.AESKey = AESKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<CouponDTO> getCouponDTOList() {
        if (couponDTOList == null)
            couponDTOList = new ArrayList<>();
        return couponDTOList;
    }

    public void setCouponDTOList(List<CouponDTO> couponDTOList) {
        this.couponDTOList = couponDTOList;
    }

    public List<CourseTeacherDTO> getCourseTeacherDTOs() {
        if (courseTeacherDTOs == null)
            courseTeacherDTOs = new ArrayList<>();
        return courseTeacherDTOs;
    }

    public void setCourseTeacherDTOs(List<CourseTeacherDTO> courseTeacherDTOs) {
        this.courseTeacherDTOs = courseTeacherDTOs;
    }
}


