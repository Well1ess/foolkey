package com.example.a29149.yuyuan.ModelStudent.Me.info;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.Enum.SchoolEnum;
import com.example.a29149.yuyuan.Enum.SexTagEnum;
import com.example.a29149.yuyuan.Enum.TechnicTagEnum;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.a29149.yuyuan.controller.userInfo.UpdateStudentInfoController;
import com.example.resource.util.image.GlideCircleTransform;

/**
 * 更新个人的一些非关键信息（比如，昵称，口号）
 * 不包括头像、密码、手机号
 * Created by geyao on 2017/5/23.
 */

public class ModifyMyInfoActivity extends Activity implements View.OnClickListener{

    //保存我的信息的DTO
    private StudentDTO studentDTO;

    @ViewInject(R.id.nickedName)
    private EditText nickedName;

    @ViewInject(R.id.sex)
    private TextView sex;

    @ViewInject(R.id.school)
    private EditText school;

//    @ViewInject(R.id.birthday)
//    private EditText birthday;

    @ViewInject(R.id.slogan)
    private EditText slogan;

    @ViewInject(R.id.email)
    private EditText email;

    @ViewInject(R.id.github)
    private EditText github;

    @ViewInject(R.id.organization)
    private EditText organization;

    @ViewInject(R.id.technicTag)
    private TextView technicTag;

    @ViewInject(R.id.description)
    private EditText description;

    @ViewInject(R.id.photo_circle)
    private ImageView imageView;

    @ViewInject(R.id.iv_return)
    private ImageView returnLog;


    //2个较为特殊枚举，他们会在选择标签时指定
    private TechnicTagEnum technicTagEnum = TechnicTagEnum.Java;
    private SexTagEnum sexTagEnum = SexTagEnum.保密;

    //Glide依赖
    private RequestManager glide;

    private UpdateStudentInfoController updateStudentInfoController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_modify_info);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        studentDTO = GlobalUtil.getInstance().getStudentDTO();

        nickedName.setText( studentDTO.getNickedName() );
        String sexStr;
        if (studentDTO.getSexTagEnum() == null )
            sexStr = null;
        else
            sexStr = studentDTO.getSexTagEnum().toString();
        sex.setText( studentDTO.getSexTagEnum().toString() );
        String schoolStr;
        if ( studentDTO.getSchoolEnum() == null)
            schoolStr = null;
        else
            schoolStr = studentDTO.getSchoolEnum().toString();
        school.setText( schoolStr );
        String birStr;
        if (studentDTO.getBirthday() == null)
            birStr = null;
        else
            birStr = studentDTO.toString();
//        birthday.setText( birStr );
        slogan.setText( studentDTO.getSlogan() );
        email.setText( studentDTO.getEmail() );
        organization.setText( studentDTO.getOrganization() );
        github.setText( studentDTO.getGithubUrl() );
        String tag;
        if ( studentDTO.getTechnicTagEnum() == null)
            tag = null;
        else
            tag = studentDTO.getTechnicTagEnum().toString();
        technicTag.setText( tag );
        description.setText( studentDTO.getDescription() );

        //图片加载器
        glide = Glide.with(this);
        glide.load(PictureInfoBO.getOnlinePhoto( studentDTO.getUserName() ))
                .transform(new GlideCircleTransform(this))
                .into(imageView);

        technicTag.setOnClickListener(this);
        sex.setOnClickListener(this);
        returnLog.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.iv_return:
                finish();
                break;
            case R.id.text_save:
                save(v);
                break;
            case R.id.technicTag:
                chooseTag();
                break;
            case R.id.sex:
                chooseSxe();
                break;
            default:
                break;
        }
    }


    @OnClick(R.id.text_save)
    public void save(View view){
        //点击保存

        //要对用户的输入进行各种判断
        studentDTO.setNickedName( nickedName.getText().toString() );
        studentDTO.setSexTagEnum( sexTagEnum );
        try {
            studentDTO.setSchoolEnum(SchoolEnum.valueOf(school.getText().toString()));
        }catch (IllegalArgumentException e){
            studentDTO.setSchoolEnum( SchoolEnum.其他 );
        }
//        studentDTO.setBirthday( Date.parse(  ) ) );
        studentDTO.setSlogan( slogan.getText().toString() );
        studentDTO.setEmail( email.getText().toString() );
        studentDTO.setOrganization( organization.getText().toString() );
        studentDTO.setGithubUrl( github.getText().toString() );

//        TechnicTagEnum tagEnum;
//        try {
//            tagEnum = TechnicTagEnum.valueOf( technicTag.getText().toString() );
//        }catch (IllegalArgumentException e){
//            tagEnum = null;
//        }
        studentDTO.setTechnicTagEnum( technicTagEnum );

        studentDTO.setDescription( description.getText().toString() );


        updateStudentInfoController = new UpdateStudentInfoController();
        UpdateInfoAction updateInfoAction = new UpdateInfoAction();
        updateInfoAction.execute();


    }


    //弹出课程选择标签
    private AlertDialog alertDialogChooseTag;
    //这里会设置传输的技术标签
    public void chooseTag(){
        TextView customTitle = new TextView(this);
        customTitle.setPadding(0, 20, 0, 0);
        customTitle.setText("请选择内容标签");
        customTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
        customTitle.setTextSize(18);
        customTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        customTitle.setGravity(Gravity.CENTER);


        ArrayAdapter<String> teachTypeItem = new ArrayAdapter<>(this,
                R.layout.dialog_team_project_item,
                Const.REWARD_TAG);

        alertDialogChooseTag = new AlertDialog.Builder(this)
                .setCustomTitle(customTitle)
                .setAdapter(teachTypeItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        technicTag.setText("关注的领域："+Const.REWARD_TAG[which]);
                        technicTagEnum = TechnicTagEnum.valueOf( Const.REWARD_TAG[which] );
                        dialog.dismiss();
                    }
                }).create();
        alertDialogChooseTag.show();
    }

    //这里会设置传输的性别标签
    private AlertDialog alertDialogChooseSex;
    public void chooseSxe(){
        TextView customTitle = new TextView(this);
        customTitle.setPadding(0, 20, 0, 0);
        customTitle.setText("请选择");
        customTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
        customTitle.setTextSize(18);
        customTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        customTitle.setGravity(Gravity.CENTER);


        ArrayAdapter<String> teachTypeItem = new ArrayAdapter<>(this,
                R.layout.dialog_team_project_item,
                Const.SEX_Tag);

        alertDialogChooseSex = new AlertDialog.Builder(this)
                .setCustomTitle(customTitle)
                .setAdapter(teachTypeItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sex.setText("性别："+Const.SEX_Tag[which]);
                        sexTagEnum = SexTagEnum.valueOf( Const.SEX_Tag[which] );
                        dialog.dismiss();
                    }
                }).create();
        alertDialogChooseSex.show();
    }


    //修改个人信息，但关键的信息，这里不与更改
    public class UpdateInfoAction extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... strings) {
            updateStudentInfoController.setNickedName( studentDTO.getNickedName() );
            updateStudentInfoController.setSexTagEnum( studentDTO.getSexTagEnum() );
            updateStudentInfoController.setSchoolEnum( studentDTO.getSchoolEnum() );
            updateStudentInfoController.setBirthday( studentDTO.getBirthday() );
            updateStudentInfoController.setSlogan( studentDTO.getSlogan() );
            updateStudentInfoController.setEmail( studentDTO.getEmail() );
            updateStudentInfoController.setOrganization( studentDTO.getOrganization() );
            updateStudentInfoController.setGithubUrl( studentDTO.getGithubUrl() );
            updateStudentInfoController.setTechnicTagEnum( studentDTO.getTechnicTagEnum() );
            updateStudentInfoController.setDescription( studentDTO.getDescription() );

            return updateStudentInfoController.execute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (updateStudentInfoController.getResult().equals("success")) {

                Toast.makeText(ModifyMyInfoActivity.this, "已保存", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(ModifyMyInfoActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
