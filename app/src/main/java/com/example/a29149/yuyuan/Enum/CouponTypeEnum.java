package com.example.a29149.yuyuan.Enum;

/**
 * Created by 张丽华 on 2017/5/2.
 * Description:
 */

public enum CouponTypeEnum {

    全场, 仅提问, 仅围观, 购买课程;


    public boolean checkType(CourseTypeEnum courseTypeEnum) {

        switch (this) {

            case 全场: {

                return true;

            }

            case 仅围观: {

                return courseTypeEnum.compareTo(CourseTypeEnum.围观) == 0;

            }

            case 仅提问: {

                return courseTypeEnum.compareTo(CourseTypeEnum.提问) == 0;

            }

            case 购买课程: {

                return courseTypeEnum.compareTo(CourseTypeEnum.老师课程) == 0

                        || courseTypeEnum.compareTo(CourseTypeEnum.学生悬赏) == 0;

            }

        }

        return false;

    }

}
