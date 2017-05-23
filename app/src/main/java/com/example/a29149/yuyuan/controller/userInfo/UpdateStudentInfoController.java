package com.example.a29149.yuyuan.controller.userInfo;

import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsStudentDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.Enum.SchoolEnum;
import com.example.a29149.yuyuan.Enum.SexTagEnum;
import com.example.a29149.yuyuan.Enum.TechnicTagEnum;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.controller.AbstractControllerTemplate;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * 修改个人资料
 * Created by GR on 2017/5/23.
 */

public class UpdateStudentInfoController extends AbstractControllerTemplate {

    /**
     * 传送到后台的数据
     **/
    //性别
    private SexTagEnum sexTagEnum;
    //公司
    private String organization;
    //生日
    private Date birthday;
    //技术标签
    private TechnicTagEnum technicTagEnum;
    //毕业院校
    private SchoolEnum schoolEnum;
    //
    private String githubUrl;
    //昵称
    private String nickedName;
    //签名
    private String slogan;
    //
    private String email;
    //
    private String description;

    /**
     * 后台传来的数据
     */
    //结果
    private String result = Const.FAIL;
    //修改后的学生信息
    private StudentDTO studentDTO;

    @Override
    public void handle() throws JSONException {
        super.url += "/aes/studentInfo/updateInfo";

        super.jsonObject.put("sexTagEnum", sexTagEnum);
        super.jsonObject.put("organization", organization);
        super.jsonObject.put("birthday", birthday);
        super.jsonObject.put("technicTagEnum", technicTagEnum);
        super.jsonObject.put("schoolEnum", schoolEnum);
        super.jsonObject.put("githubUrl", githubUrl);
        super.jsonObject.put("nickedName", nickedName);
        super.jsonObject.put("slogan", slogan);
        super.jsonObject.put("email", email);
        super.jsonObject.put("description", description);
    }

    @Override
    protected void afterHandle(String s) {
        JSONObject jsonObject = null;
        try {

            //把后台传来的数据String转为JSON
            jsonObject = new JSONObject(s);
            String resultFlag = jsonObject.getString("result");
            if(resultFlag == null){
                resultFlag = Const.FAIL;
            }
            //根据返回的结果标志进行不同的操作
            if (resultFlag.equals("success")) {


                //判断传来的参数是否是空
                if (jsonObject.getString("studentDTO") == null) {
                    this.result = Const.FAIL;
                    return;
                }

                //处理参数：studentDTO（修改后的学生信息）
                java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<StudentDTO>() {
                }.getType();
                this.studentDTO = new Gson().fromJson(jsonObject.getString("studentDTO"), type);

                this.result = Const.SUCCESS;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            this.result = Const.FAIL;
        }
    }

    public void setSexTagEnum(SexTagEnum sexTagEnum) {
        this.sexTagEnum = sexTagEnum;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setTechnicTagEnum(TechnicTagEnum technicTagEnum) {
        if (technicTagEnum != null)
            this.technicTagEnum = technicTagEnum;
        else
            this.technicTagEnum = TechnicTagEnum.Java;
    }

    public void setSchoolEnum(SchoolEnum schoolEnum) {
        this.schoolEnum = schoolEnum;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public void setNickedName(String nickedName) {
        this.nickedName = nickedName;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getResult() {
        return result;
    }

    public StudentDTO getStudentDTO() {
        return studentDTO;
    }
}
