package com.example.a29149.yuyuan.Util;

import com.example.a29149.yuyuan.DTO.ApplicationRewardWithTeacherSTCDTO;
import com.example.a29149.yuyuan.DTO.ApplicationStudentRewardAsStudentSTCDTO;
import com.example.a29149.yuyuan.DTO.ApplicationStudentRewardDTO;
import com.example.a29149.yuyuan.DTO.CouponDTO;
import com.example.a29149.yuyuan.DTO.CourseStudentDTO;
import com.example.a29149.yuyuan.DTO.CourseStudentPopularDTO;
import com.example.a29149.yuyuan.DTO.CourseTeacherPopularDTO;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsStudentDTO;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsTeacherSTCDTO;
import com.example.a29149.yuyuan.DTO.OrderBuyRewardAsTeacherSTCDTO;
import com.example.a29149.yuyuan.DTO.RewardDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.DTO.TeacherAllInfoDTO;
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
    //学生所拥有的所有悬赏信息
    private List<ApplicationStudentRewardAsStudentSTCDTO> applicationStudentRewardAsStudentSTCDTOs;
    //悬赏信息DTO
    private RewardDTO rewardDTO;

    //申请悬赏信息DTO和老师信息DTO
    private List<ApplicationRewardWithTeacherSTCDTO> applicationRewardWithTeacherSTCDTOs;
    //老师所有信息DTO
    private TeacherAllInfoDTO teacherAllInfoDTO;

    //老师界面的课程所有信息
    private List<OrderBuyCourseAsTeacherSTCDTO> orderBuyCourseAsTeacherSTCDTOs;

    //未评价订单
    private List<OrderBuyCourseAsStudentDTO> orderBuyCourseAsStudentDTOs;


    //悬赏和学生信息
    private List<OrderBuyRewardAsTeacherSTCDTO> orderBuyRewardAsTeacherSTCDTOs;

    //申请悬赏的DTO
    private ApplicationStudentRewardDTO applicationStudentRewardDTO;
    //学生发布的悬赏列表
    private List<CourseStudentDTO> courseStudentDTOs;
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

    //老师UI——课程
    private List<String> teacherUIScore;

    public List<String> getTeacherUIScore() {
        if (teacherUIScore == null){
            teacherUIScore = new ArrayList<>();
            teacherUIScore.add("1");
            teacherUIScore.add("2");
        }
        return teacherUIScore;
    }

    public void setTeacherUIScore(List<String> teacherUIScore) {
        this.teacherUIScore = teacherUIScore;
    }

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
        if (rewardChooseContent == null)
            rewardChooseContent = new String[]{"","","","","不限","小白","不限","不限"};
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


    public List<CourseStudentDTO> getCourseStudentDTOs() {
        return courseStudentDTOs;
    }

    public void setCourseStudentDTOs(List<CourseStudentDTO> courseStudentDTOs) {
        this.courseStudentDTOs = courseStudentDTOs;
    }


    public List<ApplicationStudentRewardAsStudentSTCDTO> getApplicationStudentRewardAsStudentSTCDTOs() {
        if (applicationRewardWithTeacherSTCDTOs == null)
            applicationRewardWithTeacherSTCDTOs = new ArrayList<>();
        return applicationStudentRewardAsStudentSTCDTOs;
    }

    public void setApplicationStudentRewardAsStudentSTCDTOs(List<ApplicationStudentRewardAsStudentSTCDTO> applicationStudentRewardAsStudentSTCDTOs) {
        this.applicationStudentRewardAsStudentSTCDTOs = applicationStudentRewardAsStudentSTCDTOs;
    }

    public RewardDTO getRewardDTO() {
        return rewardDTO;
    }

    public void setRewardDTO(RewardDTO rewardDTO) {
        this.rewardDTO = rewardDTO;
    }

    public List<ApplicationRewardWithTeacherSTCDTO> getApplicationRewardWithTeacherSTCDTOs() {
        return applicationRewardWithTeacherSTCDTOs;
    }

    public void setApplicationRewardWithTeacherSTCDTOs(List<ApplicationRewardWithTeacherSTCDTO> applicationRewardWithTeacherSTCDTOs) {
        this.applicationRewardWithTeacherSTCDTOs = applicationRewardWithTeacherSTCDTOs;
    }


    public TeacherAllInfoDTO getTeacherAllInfoDTO() {
        return teacherAllInfoDTO;
    }

    public void setTeacherAllInfoDTO(TeacherAllInfoDTO teacherAllInfoDTO) {
        this.teacherAllInfoDTO = teacherAllInfoDTO;
    }


    public ApplicationStudentRewardDTO getApplicationStudentRewardDTO() {
        return applicationStudentRewardDTO;
    }

    public void setApplicationStudentRewardDTO(ApplicationStudentRewardDTO applicationStudentRewardDTO) {
        this.applicationStudentRewardDTO = applicationStudentRewardDTO;
    }


    public List<OrderBuyCourseAsTeacherSTCDTO> getOrderBuyCourseAsTeacherSTCDTOs() {
        return orderBuyCourseAsTeacherSTCDTOs;
    }

    public void setOrderBuyCourseAsTeacherSTCDTOs(List<OrderBuyCourseAsTeacherSTCDTO> orderBuyCourseAsTeacherSTCDTOs) {
        this.orderBuyCourseAsTeacherSTCDTOs = orderBuyCourseAsTeacherSTCDTOs;
    }


    public List<OrderBuyCourseAsStudentDTO> getOrderBuyCourseAsStudentDTOs() {
        return orderBuyCourseAsStudentDTOs;
    }

    public void setOrderBuyCourseAsStudentDTOs(List<OrderBuyCourseAsStudentDTO> orderBuyCourseAsStudentDTOs) {
        this.orderBuyCourseAsStudentDTOs = orderBuyCourseAsStudentDTOs;
    }

    public List<OrderBuyRewardAsTeacherSTCDTO> getOrderBuyRewardAsTeacherSTCDTOs() {
        return orderBuyRewardAsTeacherSTCDTOs;
    }

    public void setOrderBuyRewardAsTeacherSTCDTOs(List<OrderBuyRewardAsTeacherSTCDTO> orderBuyRewardAsTeacherSTCDTOs) {
        this.orderBuyRewardAsTeacherSTCDTOs = orderBuyRewardAsTeacherSTCDTOs;
    }


}


