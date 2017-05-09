package com.example.a29149.yuyuan.Util;

import com.example.a29149.yuyuan.DTO.CouponDTO;
import com.example.a29149.yuyuan.DTO.CourseStudentPopularDTO;
import com.example.a29149.yuyuan.DTO.CourseTeacherPopularDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.DTO.TeacherDTO;

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
    private List<CourseTeacherPopularDTO> courseTeacherPopularDTOs;
    //学生悬赏列表
    private List<CourseStudentPopularDTO> courseStudentPopularDTOs;
    //学生信息
    private StudentDTO studentDTO;
    //老师信息
    private TeacherDTO teacherDTO;
    //用户登录获取的id，用以小米推送
    private String id;
    //用户记录发布悬赏时填写的信息
    private String[] rewardChooseContent = new String[8];
    //用户记录发布课程时填写的信息
    private String[] courseChooseContent = new String[7];
    //搜索用的List
    private List<Object> courseList;
    private List<Object> rewardList;
    private List<Object> articleList;
    private List<Object> teacherList;
    private List<Object> questionList;

    public List<Object> getCourseList() {
        if (courseList == null)
            courseList = new ArrayList<>();
        return courseList;
    }

    public void setCourseList(List<Object> courseList) {
        this.courseList = courseList;
    }

    public List<Object> getRewardList() {
        if (rewardList == null)
            rewardList = new ArrayList<>();
        return rewardList;
    }

    public void setRewardList(List<Object> rewardList) {
        this.rewardList = rewardList;
    }

    public List<Object> getArticleList() {
        if (articleList == null)
            articleList = new ArrayList<>();
        return articleList;
    }

    public void setArticleList(List<Object> articleList) {
        this.articleList = articleList;
    }

    public List<Object> getTeacherList() {
        if (teacherList == null)
            teacherList = new ArrayList<>();
        return teacherList;
    }

    public void setTeacherList(List<Object> teacherList) {
        this.teacherList = teacherList;
    }

    public List<Object> getQuestionList() {
        if (questionList == null)
            questionList = new ArrayList<>();
        return questionList;
    }

    public void setQuestionList(List<Object> questionList) {
        this.questionList = questionList;
    }

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

    public List<CourseTeacherPopularDTO> getCourseTeacherPopularDTOs() {
        if (courseTeacherPopularDTOs == null)
            courseTeacherPopularDTOs = new ArrayList<>();
        return courseTeacherPopularDTOs;
    }

    public void setCourseTeacherPopularDTOs(List<CourseTeacherPopularDTO> courseTeacherPopularDTOs) {
        this.courseTeacherPopularDTOs = courseTeacherPopularDTOs;
    }

    public StudentDTO getStudentDTO() {
        if (studentDTO == null)
            studentDTO = new StudentDTO();
        return studentDTO;
    }

    public void setStudentDTO(StudentDTO studentDTO) {
        this.studentDTO = studentDTO;
    }


    public TeacherDTO getTeacherDTO() {
      /*  if (teacherDTO == null)
            teacherDTO = new TeacherDTO();*/
        return teacherDTO;
    }

    public void setTeacherDTO(TeacherDTO teacherDTO) {
        this.teacherDTO = teacherDTO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getRewardChooseContent() {
        return rewardChooseContent;
    }

    public void setRewardChooseContent(String[] rewardChooseContent) {
        this.rewardChooseContent = rewardChooseContent;
    }

    public List<CourseStudentPopularDTO> getCourseStudentPopularDTOs() {
        if (courseStudentPopularDTOs == null)
            courseStudentPopularDTOs = new ArrayList<>();
        return courseStudentPopularDTOs;
    }

    public void setCourseStudentPopularDTOs(List<CourseStudentPopularDTO> courseStudentPopularDTOs) {
        this.courseStudentPopularDTOs = courseStudentPopularDTOs;
    }
    public String[] getCourseChooseContent() {
        return courseChooseContent;
    }

    public void setCourseChooseContent(String[] courseChooseContent) {
        this.courseChooseContent = courseChooseContent;
    }

}


