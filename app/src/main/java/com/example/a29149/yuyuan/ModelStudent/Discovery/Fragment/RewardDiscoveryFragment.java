package com.example.a29149.yuyuan.ModelStudent.Discovery.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.a29149.yuyuan.AbstractObject.AbstractFragment;
import com.example.a29149.yuyuan.DTO.RewardWithStudentSTCDTO;
import com.example.a29149.yuyuan.Main.MainStudentActivity;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Activity.RewardActivity;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Adapter.RewardAdapter;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.DynamicListView;
import com.example.a29149.yuyuan.Widget.SlideRefreshLayout;
import com.example.a29149.yuyuan.controller.course.reward.GetPopularController;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//TODO 这里的数据传输依然依靠全局变量
public class RewardDiscoveryFragment extends AbstractFragment {
    private static final String TAG = "RewardDiscoveryFragment";

    //缓存当前view，方便再次切换到这个view时，不需要执行onCreateView方法
    private View view;
    private Context mContext;
    private List<RewardWithStudentSTCDTO> allRewardWithStudentSTCDTOS = new ArrayList<>(); //所有的悬赏列表

    //下拉刷新的Layout
    @ViewInject(R.id.srl_slide_layout)
    private SlideRefreshLayout mSlideLayout;

    //上划到底部动态更新的List
    @ViewInject(R.id.content)
    private DynamicListView mRewardList;

    //填充数据的Adapter
    private RewardAdapter mListAdapter;

    //记录请求的页数
    int pageNo = 1;

    public RewardDiscoveryFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 判断view有没有创建，如果创建了，则不需要重新加载。
        mContext = getContext();
        if (view == null) {
            //把跳跳跳动画放在这里，目的是第一次创建view时，才会执行动画
//            MainStudentActivity.shapeLoadingDialog.show();
            view = inflater.inflate(R.layout.fragment_reward_discovery, container, false);
            AnnotationUtil.injectViews(this, view);
            AnnotationUtil.setClickListener(this, view);

            //给每个item设置点击监听器
            mRewardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //查看某个具体的课程订单
                    Intent intent = new Intent(mContext, RewardActivity.class);
                    //新建Bundle，放置具体的DTO
                    Bundle bundle = new Bundle();
                    //从类变量的List里获取具体的DTO
                    bundle.putSerializable("DTO", allRewardWithStudentSTCDTOS.get(position));
                    //将Bundle放置在intent里，并开启新Activity
                    intent.putExtras( bundle );
                    startActivity( intent );
                }
            });


            //新建Adapter，注意，此时的Adapter并没有数据
            mListAdapter = new RewardAdapter(mContext);
            //设置列表动态加载
            //ListView其实并不关心数据，它只需要设置一个Adapter就可以了，数据应该都放置在Adapter里
            mRewardList.setOnLoadingListener(new DynamicListView.onLoadingListener() {
                @Override
                public void setLoad() {
                    //TODO:网络传输
                    GetReward getReward = new GetReward(++pageNo);
                    getReward.execute();
                }
            });

            //设置列表下拉时的刷新
            mSlideLayout.setRotateView(view.findViewById(R.id.iv_refresh));
            mSlideLayout.setOnSlideRefreshListener(
                    new SlideRefreshLayout.onSlideRefreshListener() {
                        @Override
                        public void onRefresh() {
                            //TODO:网络通信
                            //获取主页的热门课程
                            if (MainStudentActivity.shapeLoadingDialog != null) {
//                                MainStudentActivity.shapeLoadingDialog.show();
                            }
                            //由于是刷新，所以首先清空所有数据
                            pageNo = 1;
                            List<RewardWithStudentSTCDTO> rewardWithStudentSTCDTOs = new ArrayList<RewardWithStudentSTCDTO>();
                            setAllRewardWithStudentSTCDTOS(rewardWithStudentSTCDTOs);
                            GetReward getReward = new GetReward(pageNo);
                            getReward.execute();
                        }
                    });

            //TODO：数据初始化
            GetReward getReward = new GetReward(1);
            getReward.execute();
        }
        return view;
    }


    /**
     * 刷新数据，主要用于删除悬赏时，列表里也要删除
     */
    public void updateRewardList() {
        //调用adapter的notifyDateChanged方法
        //TODO
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //根据页码获取热门悬赏
    public class GetReward extends AsyncTask<String, Integer, String> {
        int pageNo;

        public GetReward(int pageNo) {
            super();
            this.pageNo = pageNo;
        }

        @Override
        protected String doInBackground(String... params) {
            return GetPopularController.execute(pageNo + "");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                try {
                    log.d(this, result);
                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");

                    if (resultFlag.equals("success")) {

                        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<RewardWithStudentSTCDTO>>() {
                        }.getType();
                        List<RewardWithStudentSTCDTO> courseStudentDTOS = new Gson().fromJson(jsonObject.getString("rewardCourseDTOS"), type);

                        //若>1则表示分页存取
                        if (pageNo == 1) {
                            setAllRewardWithStudentSTCDTOS(courseStudentDTOS);
                            //TODO
                            mListAdapter.setData(courseStudentDTOS);
                            //绑定listView与Adapter
                            mRewardList.setAdapter(mListAdapter);
                        } else if (pageNo > 1) {
                            //TODO 这样其实并不能保证数据不重复
                            allRewardWithStudentSTCDTOS.addAll(courseStudentDTOS);
                            mListAdapter.addExtinctDataList(courseStudentDTOS, false);
                            mRewardList.onLoadFinish();
                        }

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                                mListAdapter.notifyDataSetChanged();
                                MainStudentActivity.shapeLoadingDialog.dismiss();
                            }
                        }, 1000);

                    } else {
                        Toast.makeText(getActivity(), "网络连接失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "网络连接失败！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "网络连接失败！", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public List<RewardWithStudentSTCDTO> getAllRewardWithStudentSTCDTOS() {
        return allRewardWithStudentSTCDTOS;
    }

    public void setAllRewardWithStudentSTCDTOS(List<RewardWithStudentSTCDTO> allRewardWithStudentSTCDTOS) {
        this.allRewardWithStudentSTCDTOS = allRewardWithStudentSTCDTOS;
    }
}
