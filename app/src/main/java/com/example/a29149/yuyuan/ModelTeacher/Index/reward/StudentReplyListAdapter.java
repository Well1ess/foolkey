package com.example.a29149.yuyuan.ModelTeacher.Index.reward;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseDTO;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseWithStudentAsTeacherSTCDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.Enum.OrderStateEnum;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.ModelTeacher.Index.TeacherIndexMainFragment;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.Dialog.WarningDisplayDialog;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.a29149.yuyuan.controller.course.haveClass.EndClassController;
import com.example.a29149.yuyuan.controller.course.haveClass.StartClassController;
import com.example.resource.util.image.GlideCircleTransform;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/12/26 0026.
 * GridView的适配器，即申请该条悬赏的学生信息适配器
 */

public class StudentReplyListAdapter extends BaseAdapter implements View.OnClickListener {
    private static final String TAG = "StudentReplyListAdapter";

    private static Context mContext;
    private List<OrderBuyCourseWithStudentAsTeacherSTCDTO> orderBuyCourseWithStudentAsTeacherSTCDTOs;
    private StudentDTO mStudentDTO;//学生信息
    private OrderBuyCourseDTO mOrderBuyCourseDTO; //订单信息
    //点击开始或取消时的警告
    private WarningDisplayDialog.Builder mDisplayDialog;
    private MyViewHolder myViewHolder = null;
    //图片加载
    private RequestManager glide;

    public StudentReplyListAdapter(Context context, List<OrderBuyCourseWithStudentAsTeacherSTCDTO> strings) {
        this.mContext = context;
        this.orderBuyCourseWithStudentAsTeacherSTCDTOs = strings;
    }

    public void update() {
        this.notifyDataSetInvalidated();
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

        if (convertView == null) {
            convertView = LayoutInflater
                    .from(mContext)
                    .inflate(R.layout.gridview_student_state_item_layout, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        //myViewHolder.mPhoto.setText(orderBuyCourseWithStudentAsTeacherSTCDTOs.get(position).getTeacherAllInfoDTO().getNickedName());
        mStudentDTO = orderBuyCourseWithStudentAsTeacherSTCDTOs.get(position).getStudentDTO();
        mOrderBuyCourseDTO = orderBuyCourseWithStudentAsTeacherSTCDTOs.get(position).getOrderBuyCourseDTO();
        Log.i("malei", mOrderBuyCourseDTO.getOrderStateEnum() + "");
        String stateStudent = mOrderBuyCourseDTO.getOrderStateEnum() + "";
        Log.d(TAG, "getView: " + stateStudent);
        if (stateStudent.equals("同意上课"))
            stateStudent = "开始上课";
        else if (stateStudent.equals("上课中"))
            stateStudent = "点击下课";
        else if (stateStudent.equals("结束上课"))
            stateStudent = "已完成";
        myViewHolder.state.setText(stateStudent);
        myViewHolder.tv.setOnClickListener(this);
        glide = Glide.with(mContext);
        glide.load(PictureInfoBO.getOnlinePhoto(mStudentDTO.getUserName()))
                .error(R.drawable.photo_placeholder1)
                .transform(new GlideCircleTransform(mContext))
                .into(myViewHolder.tv);
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
                else if (mOrderBuyCourseDTO.getOrderStateEnum().compareTo(OrderStateEnum.上课中) == 0)
                    new endClassAction().execute();
                else if (mOrderBuyCourseDTO.getOrderStateEnum().compareTo(OrderStateEnum.结束上课) == 0)
                    ;
                dialog.dismiss();
            }
        });
        mDisplayDialog.create();
        return convertView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.sqi_photo:
                startOrEndClass();
                break;
            default:
                break;
        }

    }

    private void startOrEndClass() {
        String s = myViewHolder.state.getText().toString();
        Log.i("malei", s);
        switch (s) {
            case "开始上课":
                mDisplayDialog.setMsg("是否开始上课？再次点击后下课！");
                mDisplayDialog.getDialog().show();
                break;
            case "点击下课":
                mDisplayDialog.setMsg("是否下课？点击后完成课程！");
                mDisplayDialog.getDialog().show();
                break;
            case "已完成":
                mDisplayDialog.setMsg("已经下课了。可以去订单里面查看！");
                mDisplayDialog.getDialog().show();
                break;
        }

    }

    static class MyViewHolder {

        ImageView tv;
        TextView state;

        public MyViewHolder(View view) {
            tv = (ImageView) view.findViewById(R.id.sqi_photo);
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
                        myViewHolder.state.setText("点击下课");
                        update();
                        mOrderBuyCourseDTO.setOrderStateEnum(OrderStateEnum.上课中);

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
                        mOrderBuyCourseDTO.setOrderStateEnum(OrderStateEnum.结束上课);
                        myViewHolder.state.setText("已完成");
                        update();
//                        Intent intent = new Intent(mContext,TeacherIndexMainFragment.class);
//                        mContext.startActivity(intent);

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

