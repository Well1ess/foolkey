package com.example.a29149.yuyuan.Model.Me.Reward;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.ApplicationRewardWithTeacherSTCDTO;
import com.example.a29149.yuyuan.DTO.ApplicationStudentRewardAsStudentSTCDTO;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张丽华 on 2017/5/10.
 * Description:我的悬赏的适配器
 */

public class OwnerRewardListAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> strings;

    private List<String> strings1;

    private List<String> strings2;

    private List<String> strings3;

    private List<List> sum;

    //用于保存每个列表是否展开
    private List<State> listState;

    //展开时的标签
    private Drawable open;
    //关闭时的标签
    private Drawable close;
    //我的所有悬赏
    private List<ApplicationStudentRewardAsStudentSTCDTO> list;
    //某条悬赏所拥有的申请的老师信息和申请信息
    private List<ApplicationRewardWithTeacherSTCDTO> applicationRewardWithTeacherSTCDTOList;

    public OwnerRewardListAdapter(Context context) {
        mContext = context;
        list = GlobalUtil.getInstance().getApplicationStudentRewardAsStudentSTCDTOs();

        strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");

        strings1 = new ArrayList<>();
        strings1.add("1");
        strings1.add("2");
        strings1.add("1");
        strings1.add("2");

        strings2 = new ArrayList<>();
        strings2.add("1");
        strings2.add("2");
        strings2.add("1");
        strings2.add("2");

        strings3 = new ArrayList<>();
        strings3.add("1");
        strings3.add("2");
        strings3.add("1");
        strings3.add("2");

        sum = new ArrayList<>();
        sum.add(strings);
        sum.add(strings1);
        sum.add(strings2);
        sum.add(strings3);

        listState = new ArrayList<>();
        for (int i = 0; i < sum.size(); i++) {
            listState.add(new State(false, Integer.MAX_VALUE));
        }

        close = mContext.getResources().getDrawable(android.R.drawable.arrow_down_float);
        open = mContext.getResources().getDrawable(android.R.drawable.arrow_up_float);
    }

    @Override
    public int getCount() {
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
            applicationRewardWithTeacherSTCDTOList = list.get(position).getApplicationRewardWithTeacherSTCDTOS();
            TeacherReplyListAdapter teacherReplyListAdapter = new TeacherReplyListAdapter(mContext, applicationRewardWithTeacherSTCDTOList);
            final GridView gridView = (GridView) convertView.findViewById(R.id.teacher_head);
            gridView.setAdapter(teacherReplyListAdapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext, "s", Toast.LENGTH_SHORT).show();
                }
            });

            if (listState.get(position).getOriginalHeight() > gridView.getLayoutParams().height)
                listState.get(position).setOriginalHeight(gridView.getLayoutParams().height);

            final TextView textView = (TextView) convertView.findViewById(R.id.open_close);

            //获取悬赏标题
            final TextView title = (TextView) convertView.findViewById(R.id.tv_title);
            title.setText(GlobalUtil.getInstance().getApplicationStudentRewardAsStudentSTCDTOs().get(position).getRewardDTO().getTopic());
            //获取悬赏价格
            final TextView price = (TextView) convertView.findViewById(R.id.tv_price);
            price.setText(GlobalUtil.getInstance().getApplicationStudentRewardAsStudentSTCDTOs().get(position).getRewardDTO().getPrice()+"");
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
