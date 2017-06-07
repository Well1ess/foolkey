package com.example.a29149.yuyuan.ModelTeacher.TeacherMain.Score.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.a29149.yuyuan.DTO.OrderBuyCourseWithStudentAsTeacherSTCDTO;
import com.example.a29149.yuyuan.DTO.OrderBuyRewardAsTeacherSTCDTO;
import com.example.a29149.yuyuan.ModelTeacher.Index.course.StudentReplyListAdapter;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张丽华 on 2017/5/10.
 * Description:我的课程的适配器
 */
@Deprecated
public class OwnerRewardListAdapter extends BaseAdapter {

    private Context mContext;

    //用于保存每个列表是否展开
    private List<State> listState;

    //展开时的标签
    private Drawable open;
    //关闭时的标签
    private Drawable close;
    //我的所有悬赏
    private List<OrderBuyRewardAsTeacherSTCDTO> list;
    //某条悬赏所拥有的申请的学生信息和订单信息
    private List<OrderBuyCourseWithStudentAsTeacherSTCDTO> orderBuyCourseWithStudentAsTeacherSTCDTOs;

    public OwnerRewardListAdapter(Context context) {
        mContext = context;
        list = GlobalUtil.getInstance().getOrderBuyRewardAsTeacherSTCDTOs();

        listState = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            listState.add(new State(false, Integer.MAX_VALUE));
        }
        close = mContext.getResources().getDrawable(android.R.drawable.arrow_down_float);
        open = mContext.getResources().getDrawable(android.R.drawable.arrow_up_float);
    }

    @Override
    public int getCount() {
        Log.i("malei",list.size()+"");
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_owner_reward, null);
            //获取申请的老师信息和申请信息
            orderBuyCourseWithStudentAsTeacherSTCDTOs = list.get(position).getOrderBuyCourseWithStudentAsTeacherSTCDTOS();
            Log.i("malei",orderBuyCourseWithStudentAsTeacherSTCDTOs.get(0).getOrderBuyCourseDTO().toString());
            StudentReplyListAdapter studentReplyListAdapter = new StudentReplyListAdapter(mContext, orderBuyCourseWithStudentAsTeacherSTCDTOs);
            final GridView gridView = (GridView) convertView.findViewById(R.id.gv_apply_user_head);
            gridView.setAdapter(studentReplyListAdapter);
            //点击申请接单的老师的头像，跳转到老师的详细信息中
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int positionIn, long id) {
                    /*Intent intent = new Intent(mContext,StudentIndexActivity.class);
                    intent.putExtra("positionIn",positionIn+"");//gridView的position
                    Log.i("malei",positionIn+"InOwner");
                    intent.putExtra("positionOut",position+"");//gridView的position
                    Log.i("malei",position+"OutOwner");//listview的position
                    mContext.startActivity(intent);*/
                }
            });

            if (listState.get(position).getOriginalHeight() > gridView.getLayoutParams().height)
                listState.get(position).setOriginalHeight(gridView.getLayoutParams().height);

            final TextView textView = (TextView) convertView.findViewById(R.id.tv_open_close);

            //获取悬赏标题
            final TextView title = (TextView) convertView.findViewById(R.id.title);
            title.setText(GlobalUtil.getInstance().getOrderBuyRewardAsTeacherSTCDTOs().get(position).getRewardDTO().getTopic());
            //获取悬赏价格
            final TextView price = (TextView) convertView.findViewById(R.id.tv_price);
            price.setText(GlobalUtil.getInstance().getOrderBuyRewardAsTeacherSTCDTOs().get(position).getRewardDTO().getPrice()+"");
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (listState.get(position).isOpen()){
                        ViewGroup.LayoutParams params = gridView.getLayoutParams();
                        params.height = listState.get(position).getOriginalHeight();
                        gridView.setLayoutParams(params);

                        listState.get(position).setOpen(false);

                        textView.setCompoundDrawables(open, null, null, null);
                        textView.setText("展开");
                    }
                    else {
                        fixListViewHeight(gridView);
                        listState.get(position).setOpen(true);
                        textView.setCompoundDrawables(close, null, null, null);
                        textView.setText("关闭");
                    }
                }
            });

        }
        return convertView;
    }

    public void fixListViewHeight(GridView listView) {
        // 如果没有设置数据适配器，则ListView没有子项，返回。  
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        if (listAdapter == null) {
            return;
        }
        for (int index = 0, len = listAdapter.getCount(); index < len; index++) {
            View listViewItem = listAdapter.getView(index, null, listView);
            // 计算子项View 的宽高   
            listViewItem.measure(0, 0);
            // 计算所有子项的高度和
            totalHeight += listViewItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // listView.getDividerHeight()获取子项间分隔符的高度   
        // params.height设置ListView完全显示需要的高度    

        params.height = totalHeight;
        listView.setLayoutParams(params);
    }

    class State {
        private boolean isOpen;
        private int OriginalHeight;

        public State(boolean isOpen, int originalHeight) {
            this.isOpen = isOpen;
            OriginalHeight = originalHeight;
        }

        public boolean isOpen() {
            return isOpen;
        }

        public void setOpen(boolean open) {
            isOpen = open;
        }

        public int getOriginalHeight() {
            return OriginalHeight;
        }

        public void setOriginalHeight(int originalHeight) {
            OriginalHeight = originalHeight;
        }
    }
}
