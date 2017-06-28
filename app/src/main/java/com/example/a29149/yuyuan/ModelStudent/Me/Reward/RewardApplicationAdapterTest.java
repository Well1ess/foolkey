package com.example.a29149.yuyuan.ModelStudent.Me.Reward;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.AbstractObject.YYBaseAdapter;
import com.example.a29149.yuyuan.AbstractObject.YYLayoutManager;
import com.example.a29149.yuyuan.DTO.ApplicationRewardWithTeacherSTCDTO;
import com.example.a29149.yuyuan.DTO.ApplicationStudentRewardAsStudentSTCDTO;
import com.example.a29149.yuyuan.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author:       geyao
 * Date:         2017/6/15
 * Email:        gy2016@mail.ustc.edu.cn
 * Description:  每个悬赏的填充，但是具体的老师头像的填充，由其他Adapter来做
 * Created by geyao on 2017/6/15.
 */

//TODO 修改这里的gridView到RecyclerView 然后修改 TeacherApplicationAdapter
public class RewardApplicationAdapterTest extends YYBaseAdapter<ApplicationStudentRewardAsStudentSTCDTO> {
    private static final String TAG = "RewardApplicationAdapte";



    /**
     * @param dataList 数据
     * @param context  上下文
     *
     * @discription 构造函数
     * @author 29149
     * @time 2017/6/10 15:12
     */
    public RewardApplicationAdapterTest(List dataList, Context context) {
        super(dataList, context);
    }

    /**
     * 构造函数
     *
     * @param mContext 上下文
     */
    public RewardApplicationAdapterTest(Context mContext) {
        super(mContext);
    }

    @Override
    public View createView(final int position, View convertView, LayoutInflater layoutInflater) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_owner_reward, null);
            //获取申请的老师信息和申请信息
            final List<ApplicationRewardWithTeacherSTCDTO> applicationRewardWithTeacherSTCDTOList;
            //避免NPE
            if (getDataList().get(position).getApplicationRewardWithTeacherSTCDTOS() == null)
                applicationRewardWithTeacherSTCDTOList = new ArrayList<>();
            else
                applicationRewardWithTeacherSTCDTOList = getDataList().get(position).getApplicationRewardWithTeacherSTCDTOS();
            //定义一个新的Adapter
            final TeacherApplicationAdapterTest teacherApplicationAdapter =
                    new TeacherApplicationAdapterTest(applicationRewardWithTeacherSTCDTOList, mContext, getDataList().get(position));

            //TODO 修改为recycleView的步骤，adapter修改为adapter专有的，viewHolder修改，layOutManager做水平
            final RecyclerView recyclerView = (RecyclerView) convertView.findViewById(R.id.gv_apply_user_head);
            recyclerView.setHasFixedSize(true);
            YYLayoutManager layoutManager = new YYLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);

            recyclerView.setLayoutManager( layoutManager );
            recyclerView.setAdapter(teacherApplicationAdapter);
            //点击申请接单的老师的头像，跳转到老师的详细信息中
//            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int positionIn, long id) {
//                    //新建意图跳转到老师信息页面，决定是否接收
//                    Intent intent = new Intent(mContext,TeacherInfoActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("parentDTO", getDataList().get(position));
//                    bundle.putSerializable("courseDTO", getDataList().get(position).getRewardDTO());
//                    //放置单个老师申请的信息
//                    bundle.putSerializable("DTO", applicationRewardWithTeacherSTCDTOList.get(positionIn));
//                    intent.putExtras( bundle );
//                    mContext.startActivity(intent);
//                    //TODO 这里应该做Result处理 但没有这么个函数，没法子
//                }
//            });


            //如果没有申请，改变相应的文字信息
            final TextView textView = (TextView) convertView.findViewById(R.id.tv_open_close);
            if(applicationRewardWithTeacherSTCDTOList.size() == 0)
                textView.setText("暂无申请");

            //获取悬赏标题
            final TextView title = (TextView) convertView.findViewById(R.id.title);
            title.setText(getDataList().get(position).getRewardDTO().getTopic());
            //获取悬赏价格
            final TextView price = (TextView) convertView.findViewById(R.id.tv_price);
            price.setText(getDataList().get(position).getRewardDTO().getPrice()+"");
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 查看更多的还没有做
                    Toast.makeText(mContext, "查看更多还没有做", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return convertView;
    }

    /**
     * @param data 目标数据
     *
     * @return 结果
     *
     * @discription 移出目标数据，这里注意，在 接收 悬赏 的时候，要把该item移除，判断的标准应当是reward的id
     *              但是不能再foreach循环里做add - delete操作，所以修改DTO本身
     * @author 29149
     * @time 2017/6/10 15:16
     */
    @Override
    public boolean remove(ApplicationStudentRewardAsStudentSTCDTO data) {
        return super.remove(data);
    }


}
