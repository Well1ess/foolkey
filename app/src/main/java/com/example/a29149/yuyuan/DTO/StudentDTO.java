package com.example.a29149.yuyuan.DTO;

import com.example.a29149.yuyuan.AbstractObject.AbstractDTO;
import com.example.a29149.yuyuan.Enum.RoleEnum;
import com.example.a29149.yuyuan.Enum.SchoolEnum;
import com.example.a29149.yuyuan.Enum.SexTagEnum;
import com.example.a29149.yuyuan.Enum.TechnicTagEnum;
import com.example.a29149.yuyuan.Enum.UserStateEnum;

/**
 * Created by 张丽华 on 2017/5/2.
 * Description:
 */

public class StudentDTO  extends AbstractDTO {
    private Long id;
    private String userName;
    private String passWord;

    private Double cash;

    private Double virtualCurrency;

    private Integer prestige;//声望
    private String name;
    private SexTagEnum sexTagEnum;
    private String organization;
    private java.util.Date birthday;
    private TechnicTagEnum technicTagEnum;
    private SchoolEnum schoolEnum;

    private String blogUrl;
    private String githubUrl;
    private String otherUrl;
    private String photoUrl;
    private String nickedName;


    private RoleEnum roleEnum;

    private UserStateEnum userStateEnum;
    private String slogan;

    private String email;



    private String phoneNumber;



    private String description;


    private Integer studentAverageScore;

    private Integer learningTime;
    private Integer learningNumber;



    public StudentDTO() {

        super();

    }



    @Override

    public String toString() {

        return "StudentDTO{" +

                "id=" + id +

                ", userName='" + userName + '\'' +

                ", passWord='" + passWord + '\'' +

                ", cash=" + cash +

                ", virtualCurrency=" + virtualCurrency +

                ", prestige=" + prestige +

                ", name=" + name +

                ", sexTagEnum=" + sexTagEnum +

                ", organization='" + organization + '\'' +

                ", birthday=" + birthday +

                ", technicTagEnum=" + technicTagEnum +

                ", schoolEnum=" + schoolEnum +

                ", blogUrl='" + blogUrl + '\'' +

                ", githubUrl='" + githubUrl + '\'' +

                ", otherUrl='" + otherUrl + '\'' +

                ", photoUrl='" + photoUrl + '\'' +

                ", nickedName='" + nickedName + '\'' +

                ", roleEnum=" + roleEnum +

                ", userStateEnum=" + userStateEnum +

                ", slogan='" + slogan + '\'' +

                ", email='" + email + '\'' +

                ", phoneNumber='" + phoneNumber + '\'' +

                ", description='" + description + '\'' +

                ", studentAverageScore=" + studentAverageScore +

                ", learningTime=" + learningTime +

                ", learningNumber=" + learningNumber +

                '}';

    }



    public Long getId() {

        return id;

    }



    public void setId(Long id) {

        this.id = id;

    }



    public String getUserName() {

        return userName;

    }



    public void setUserName(String userName) {

        this.userName = userName;

    }



    public String getPassWord() {

        return passWord;

    }



    public void setPassWord(String passWord) {

        this.passWord = passWord;

    }



    public Double getCash() {

        return cash;

    }



    public void setCash(Double cash) {

        this.cash = cash;

    }



    public Double getVirtualCurrency() {

        return virtualCurrency;

    }



    public void setVirtualCurrency(Double virtualCurrency) {

        this.virtualCurrency = virtualCurrency;

    }



    public Integer getPrestige() {

        return prestige;

    }



    public void setPrestige(Integer prestige) {

        this.prestige = prestige;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SexTagEnum getSexTagEnum() {

        return sexTagEnum;

    }



    public void setSexTagEnum(SexTagEnum sexTagEnum) {

        this.sexTagEnum = sexTagEnum;

    }



    public String getOrganization() {

        return organization;

    }



    public void setOrganization(String organization) {

        this.organization = organization;

    }



    public java.util.Date getBirthday() {

        return birthday;

    }



    public void setBirthday(java.util.Date birthday) {

        this.birthday = birthday;

    }



    public TechnicTagEnum getTechnicTagEnum() {

        return technicTagEnum;

    }



    public void setTechnicTagEnum(TechnicTagEnum technicTagEnum) {

        this.technicTagEnum = technicTagEnum;

    }



    public SchoolEnum getSchoolEnum() {

        return schoolEnum;

    }



    public void setSchoolEnum(SchoolEnum schoolEnum) {

        this.schoolEnum = schoolEnum;

    }



    public String getBlogUrl() {

        return blogUrl;

    }



    public void setBlogUrl(String blogUrl) {

        this.blogUrl = blogUrl;

    }



    public String getGithubUrl() {

        return githubUrl;

    }



    public void setGithubUrl(String githubUrl) {

        this.githubUrl = githubUrl;

    }



    public String getOtherUrl() {

        return otherUrl;

    }



    public void setOtherUrl(String otherUrl) {

        this.otherUrl = otherUrl;

    }



    public String getPhotoUrl() {

        return photoUrl;

    }



    public void setPhotoUrl(String photoUrl) {

        this.photoUrl = photoUrl;

    }



    public String getNickedName() {

        return nickedName;

    }



    public void setNickedName(String nickedName) {

        this.nickedName = nickedName;

    }



    public RoleEnum getRoleEnum() {

        return roleEnum;

    }



    public void setRoleEnum(RoleEnum roleEnum) {

        this.roleEnum = roleEnum;

    }



    public UserStateEnum getUserStateEnum() {

        return userStateEnum;

    }



    public void setUserStateEnum(UserStateEnum userStateEnum) {

        this.userStateEnum = userStateEnum;

    }



    public String getSlogan() {

        return slogan;

    }



    public void setSlogan(String slogan) {

        this.slogan = slogan;

    }



    public String getEmail() {

        return email;

    }



    public void setEmail(String email) {

        this.email = email;

    }



    public String getPhoneNumber() {

        return phoneNumber;

    }



    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;

    }



    public String getDescription() {

        return description;

    }



    public void setDescription(String description) {

        this.description = description;

    }



    public Integer getStudentAverageScore() {

        return studentAverageScore;

    }



    public void setStudentAverageScore(Integer studentAverageScore) {

        this.studentAverageScore = studentAverageScore;

    }



    public Integer getLearningTime() {

        return learningTime;

    }



    public void setLearningTime(Integer learningTime) {

        this.learningTime = learningTime;

    }



    public Integer getLearningNumber() {

        return learningNumber;

    }



    public void setLearningNumber(Integer learningNumber) {

        this.learningNumber = learningNumber;

    }

}

