package com.example.a29149.yuyuan.Util;

/**
 * Created by 张丽华 on 2017/5/1.
 * Description:
 */

public class Const {

    //返回结果
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    //授课方式
    public static final String[] TEACH_METHOD = {"线上", "线下", "不限"};

    public static final String[] SEX_Tag = {"男", "女", "保密"};

    //课程类型
    public static final String[] COURSE_TYPE = {"老师课程", "学生悬赏", "提问", "围观"};

    //从学生个人资料跳转到充值金额的requestCode
    public static final int FROM_ME_FRAGMENT_TO_RECHARGE = 1;
    //从学生个人资料
    public static final int FROM_ME_FRAGMENT_TO_MODIFY = 2;
    //从悬赏详情，跳到修改悬赏页面
    public static final int FROM_REWARD_TO_MODIFY = 3;

    //悬赏标签
    public static final String[] REWARD_TAG = {"Java", "C", "JavaScript", "Python", "PHP", "Html5", "Android", "iOS", "MySQL", "前端", "后端", "其它"};
    //价格名称
    public static final String PRICE_NAME = "桃子";
}
