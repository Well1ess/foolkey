package com.example.a29149.yuyuan.DTO;

import com.example.a29149.yuyuan.Enum.RoleEnum;
import com.example.a29149.yuyuan.Enum.SchoolEnum;
import com.example.a29149.yuyuan.Enum.SexTagEnum;
import com.example.a29149.yuyuan.Enum.TechnicTagEnum;
import com.example.a29149.yuyuan.Enum.UserStateEnum;
import com.example.a29149.yuyuan.Enum.VerifyStateEnum;

/**
 * Created by GR on 2017/5/5.
 */
public class TeacherAllInfoDTO {

    private Long id;

    /*******************学生共有部分信息*************/
    //用户名
    private String userName;

    //余额？
    private Double cash;

    //虚拟货币
    private Double virtualCurrency;

    //声望
    private Integer prestige;

    //姓名
    private String name;

    //性别
    private SexTagEnum sexTagEnum;

    //？
    private String organization;

    //生日
    private Date birthday;

    //技术类别，个人擅长什么类别
    private TechnicTagEnum technicTagEnum;

    //毕业院校
    private SchoolEnum schoolEnum;

    //博客地址
    private String blogUrl;

    //Github地址
    private String githubUrl;

    //其他的地址
    private String otherUrl;

    //图像的地址
    private String photoUrl;

    //昵称
    private String nickedName;

    //角色
    private RoleEnum roleEnum;

    //用户的认证
    private UserStateEnum userStateEnum;

    //
    private String slogan;

    //邮箱
    private String email;

    //手机号码
    private String phoneNumber;

    //个人简介
    private String description;

    //作为学生，平均分
    private Float studentAverageScore;

    //学习时间？
    private Float learningTime;

    //学习的课程数？
    private Integer learningNumber;

    /*******************老师特有部分信息*************/
    //作为老师的平均分
    private Float teacherAverageScore;

    //已经教了多长时间
    private Float teachingTime;

    //教了多少人
    private Integer teachingNumber;

    //?
    private VerifyStateEnum verifyState;

    //关注数
    private Integer followerNumber;


    @Override
    public String toString() {
        return "TeacherAllInfoDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", cash=" + cash +
                ", virtualCurrency=" + virtualCurrency +
                ", prestige=" + prestige +
                ", name='" + name + '\'' +
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
                ", teacherAverageScore=" + teacherAverageScore +
                ", teachingTime=" + teachingTime +
                ", teachingNumber=" + teachingNumber +
                ", verifyState=" + verifyState +
                ", followerNumber=" + followerNumber +
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
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

    public Float getStudentAverageScore() {
        return studentAverageScore;
    }

    public void setStudentAverageScore(Float studentAverageScore) {
        this.studentAverageScore = studentAverageScore;
    }

    public Float getLearningTime() {
        return learningTime;
    }

    public void setLearningTime(Float learningTime) {
        this.learningTime = learningTime;
    }

    public Integer getLearningNumber() {
        return learningNumber;
    }

    public void setLearningNumber(Integer learningNumber) {
        this.learningNumber = learningNumber;
    }

    public Float getTeacherAverageScore() {
        return teacherAverageScore;
    }

    public void setTeacherAverageScore(Float teacherAverageScore) {
        this.teacherAverageScore = teacherAverageScore;
    }

    public Float getTeachingTime() {
        return teachingTime;
    }

    public void setTeachingTime(Float teachingTime) {
        this.teachingTime = teachingTime;
    }

    public Integer getTeachingNumber() {
        return teachingNumber;
    }

    public void setTeachingNumber(Integer teachingNumber) {
        this.teachingNumber = teachingNumber;
    }

    public VerifyStateEnum getVerifyState() {
        return verifyState;
    }

    public void setVerifyState(VerifyStateEnum verifyState) {
        this.verifyState = verifyState;
    }

    public Integer getFollowerNumber() {
        return followerNumber;
    }

    public void setFollowerNumber(Integer followerNumber) {
        this.followerNumber = followerNumber;
    }
}
