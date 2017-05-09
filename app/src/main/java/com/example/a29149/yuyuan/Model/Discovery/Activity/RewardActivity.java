package com.example.a29149.yuyuan.Model.Discovery.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Widget.Dialog.WarningDisplayDialog;

public class RewardActivity extends AppCompatActivity {

    //显示选项的对话框
    private WarningDisplayDialog.Builder displayInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        AnnotationUtil.setClickListener(this);
        AnnotationUtil.injectViews(this);

        displayInfo = new WarningDisplayDialog.Builder(this);
        displayInfo.setNegativeButton("取      消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        displayInfo.setPositiveButton("接      单", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //TODO:网络传输
            }
        });
        displayInfo.create();
    }

    //为底部菜单添加监听
    @OnClick({R.id.want_learn, R.id.chart, R.id.order})
    public void setMenuListener(View view)
    {
        switch (view.getId())
        {
            case R.id.want_learn:
                //TODO:网络传输
                break;
            case R.id.chart:
                //TODO:网络传输
                break;
            case R.id.order:
                displayInfo.setMsg("您确定要此单吗？");
                displayInfo.getDialog().dismiss();
                break;
        }
    }
}
