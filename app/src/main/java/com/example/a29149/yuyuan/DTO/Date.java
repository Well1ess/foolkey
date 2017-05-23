package com.example.a29149.yuyuan.DTO;

/**
 * Created by 张丽华 on 2017/5/2.
 * Description:
 */

public class Date {
    String date;
    String day;
    String hours;
    String minutes;
    String month;
    String seconds;
    int year;

    public String getResult() {
        return year + 1900 + "年" + getMonthPlus(month) + "月" + date + "日 " + hours + ":" + minutes + ":" + seconds + "过期";
    }

    public String getMonthPlus(String month) {
        int num = Integer.parseInt(getMonth()) + 1;
        return num + "";
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public static Date formate(String string){
        return new Date();
    }
}
