package com.example.a29149.yuyuan.ModelTeacher.Index.reward;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.OrderBuyRewardAsTeacherSTCDTO;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.shapeloading.ShapeLoadingDialog;
import com.example.a29149.yuyuan.controller.order.teacher.home.GetAgreedOnClassOrderRewardByTeacherController;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import static com.example.a29149.yuyuan.Main.MainStudentActivity.shapeLoadingDialog;

/**
 * Created by MaLei on 2017/5/14.
 * Email:ml1995@mail.ustc.edu.cn
 * 老师界面：老师申请并被同意的悬赏
 */
public class OwnerRewardTeacherFragment extends Fragment {

    //课程列表
    private ListView mCourseList;

    //适配器
    private OwnerRewardListAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_owner_reward_teacher,null);

        mCourseList = (ListView) view.findViewById(R.id.lv_reward_list);

        shapeLoadingDialog = new ShapeLoadingDialog(getContext());
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);

        loadData();

        return view;
    }

    private void loadData()
    {
        //如果没有进行加载
        if (shapeLoadingDialog != null) {
            applyReward();
        }
    }


    //获取我的学生列表
    private void applyReward() {
        new ApplyRewardAction(1).execute();
    }



    /**
     * 获取老师的悬赏请求Action
     */
    public class ApplyRewardAction extends AsyncTask<String, Integer, String> {

        int pageNo;

        public ApplyRewardAction(int pageNo) {
            super();
            this.pageNo = pageNo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            shapeLoadingDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            return GetAgreedOnClassOrderRewardByTeacherController.execute(pageNo + "");

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            log.d(this, result);
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");

                    //存储所有我拥有的课程信息DTO
                    java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<OrderBuyRewardAsTeacherSTCDTO>>() {
                    }.getType();
                    List<OrderBuyRewardAsTeacherSTCDTO> orderBuyRewardAsTeacherSTCDTOs = new Gson().fromJson(jsonObject.getString("orderBuyRewardAsTeacherSTCDTOS"), type);
                    GlobalUtil.getInstance().setOrderBuyRewardAsTeacherSTCDTOs(orderBuyRewardAsTeacherSTCDTOs);

                    if (resultFlag.equals("success")) {
                        Toast.makeText(getContext(), "获取悬赏成功！", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter = new OwnerRewardListAdapter(getContext());
                                mCourseList.setAdapter(mAdapter);


                            }
                        }, 1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }finally {
                    shapeLoadingDialog.dismiss();
                }
            } else {
                Toast.makeText(getContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();
                shapeLoadingDialog.dismiss();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

}
