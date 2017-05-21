package com.example.a29149.yuyuan.Teacher.Index.course;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.ApplicationStudentRewardAsStudentSTCDTO;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseDTO;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseWithStudentAsTeacherSTCDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.Enum.OrderStateEnum;
import com.example.a29149.yuyuan.Model.Index.Course.NewCourseOrderActivity;
import com.example.a29149.yuyuan.Model.Publish.Activity.ApplyAuthenticationTeacherActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.Util.Secret.AESOperator;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.Dialog.WarningDisplayDialog;
import com.example.a29149.yuyuan.controller.course.haveClass.EndClassController;
import com.example.a29149.yuyuan.controller.course.haveClass.StartClassController;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;

/**
 * Created by Administrator on 2016/12/26 0026.
 * GridView的适配器，即申请该条悬赏的学生信息适配器
 */

public class StudentReplyListAdapter extends BaseAdapter implements View.OnClickListener {

    private static Context mContext;
    private List<OrderBuyCourseWithStudentAsTeacherSTCDTO> orderBuyCourseWithStudentAsTeacherSTCDTOs;
    private StudentDTO mStudentDTO;//学生信息
    private OrderBuyCourseDTO mOrderBuyCourseDTO; //订单信息
    //点击开始或取消时的警告
    private WarningDisplayDialog.Builder mDisplayDialog;
    private   MyViewHolder myViewHolder = null;

    public StudentReplyListAdapter(Context context, List<OrderBuyCourseWithStudentAsTeacherSTCDTO> strings)
    {
        this.mContext = context;
        this.orderBuyCourseWithStudentAsTeacherSTCDTOs = strings;
    }

    public void update()
    {
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return orderBuyCourseWithStudentAsTeacherSTCDTOs.size();
    }

    @Override
    public Object getItem(int position) {
        return orderBuyCourseWithStudentAsTeacherSTCDTOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
        {
            convertView = LayoutInflater
                    .from(mContext)
                    .inflate(R.layout.gridview_student_state_item_layout, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        }
        else
        {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        //myViewHolder.tv.setText(orderBuyCourseWithStudentAsTeacherSTCDTOs.get(position).getTeacherAllInfoDTO().getNickedName());
        mStudentDTO = orderBuyCourseWithStudentAsTeacherSTCDTOs.get(position).getStudentDTO();
        mOrderBuyCourseDTO = orderBuyCourseWithStudentAsTeacherSTCDTOs.get(position).getOrderBuyCourseDTO();
        Log.i("malei", mOrderBuyCourseDTO.getOrderStateEnum()+"");
        String stateStudent = mOrderBuyCourseDTO.getOrderStateEnum()+"";
        if (stateStudent.equals("同意上课"))
            stateStudent = "开始上课";
        myViewHolder.state.setText(stateStudent);
        myViewHolder.tv.setOnClickListener(this);
        mDisplayDialog = new WarningDisplayDialog.Builder(mContext);
        mDisplayDialog.setNegativeButton("取      消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mDisplayDialog.setPositiveButton("确      定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mOrderBuyCourseDTO.getOrderStateEnum().compareTo(OrderStateEnum.同意上课) == 0)
                    new startClassAction().execute();
                else if(mOrderBuyCourseDTO.getOrderStateEnum().compareTo( OrderStateEnum.上课中 ) == 0 )
                    new endClassAction().execute();
                //Toast.makeText(mContext, "确定", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        mDisplayDialog.create();
        return convertView;
    }

    @Override
    public void onClick(View view) {
        mDisplayDialog.setMsg("是否开始上课？再次点击后下课！");
        mDisplayDialog.getDialog().show();
        Toast.makeText(mContext, "sss", Toast.LENGTH_SHORT).show();
    }

    static class MyViewHolder {

        TextView tv;
        TextView state;

        public MyViewHolder(View view)
        {
            tv = (TextView) view.findViewById(R.id.id_num);
            state = (TextView) view.findViewById(R.id.tv_state);
        }
    }


    /**
     * 开始上课请求
     */
    public class startClassAction extends AsyncTask<String, Integer, String> {

        public startClassAction() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            return StartClassController.execute(
                    mOrderBuyCourseDTO.getId() + ""
            );
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            log.d(this, result);
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");

                    if (resultFlag.equals("success")) {

                        Toast.makeText(mContext, "开始上课成功！", Toast.LENGTH_SHORT).show();
                        myViewHolder.state.setText("下课");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    /**
     * 下课请求
     */
    public class endClassAction extends AsyncTask<String, Integer, String> {

        public endClassAction() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            return EndClassController.execute(
                    mOrderBuyCourseDTO.getId() + ""
            );

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            log.d(this, result);
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");

                    if (resultFlag.equals("success")) {

                        Toast.makeText(mContext, "下课成功！", Toast.LENGTH_SHORT).show();
                        myViewHolder.state.setText("完成");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}

