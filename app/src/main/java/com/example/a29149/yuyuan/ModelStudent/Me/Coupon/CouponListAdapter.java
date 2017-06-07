package com.example.a29149.yuyuan.ModelStudent.Me.Coupon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 张丽华 on 2017/5/2.
 * Description:
 */

public class CouponListAdapter extends BaseAdapter {

    private Map<Integer, View> mViewMap;

    private Context mContext;

    public CouponListAdapter(Context context) {
        mContext = context;
        mViewMap = new HashMap<>();
    }

    public void updateList() {
        mViewMap.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return GlobalUtil.getInstance().getCouponDTOList().size();
    }

    @Override
    public Object getItem(int position) {
        return GlobalUtil.getInstance().getCouponDTOList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mViewMap.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_coupon, null);
            mViewMap.put(position, convertView);
            TextView value = (TextView) convertView.findViewById(R.id.value);
            TextView level = (TextView) convertView.findViewById(R.id.level);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView endTime = (TextView) convertView.findViewById(R.id.end_time);
            TextView kind = (TextView) convertView.findViewById(R.id.kind);
            RelativeLayout head = (RelativeLayout) convertView.findViewById(R.id.iv_photo);

            value.setText(check(GlobalUtil.getInstance().getCouponDTOList().get(position).getValue()));
            level.setText("满" +check(GlobalUtil.getInstance().getCouponDTOList().get(position).getLevel()) + "元可用");
            title.setText(GlobalUtil.getInstance().getCouponDTOList().get(position).getName());
            endTime.setText(GlobalUtil.getInstance().getCouponDTOList().get(position).getDeadTime().getResult());
            kind.setText(GlobalUtil.getInstance().getCouponDTOList().get(position).getCouponTypeEnum().toString());

            switch (GlobalUtil.getInstance().getCouponDTOList().get(position).getCouponTypeEnum()) {

                case 全场: {
                    head.setBackgroundColor(mContext.getResources().getColor(R.color.colorSky));
                    break;
                }

                case 仅围观: {
                    head.setBackgroundColor(mContext.getResources().getColor(R.color.QQmusicYellow));
                    break;
                }

                case 仅提问: {
                    head.setBackgroundColor(mContext.getResources().getColor(R.color.rect));
                    break;
                }

                case 购买课程: {
                    head.setBackgroundColor(mContext.getResources().getColor(R.color.triangle));
                    break;
                }

            }


        }
        return convertView;
    }

    public int DoubleParseInt(Double d1) {
        Double d2 = d1 % 1;
        String str1 = new String((d1 - d2) + "");
        str1 = str1.split("\\.")[0];
        int i1 = Integer.parseInt(str1);
        return i1;
    }

    public String check(Double d1) {
        Double d2 = d1 % 1;
        if (d2 == 0.0) {
            return DoubleParseInt(d1) + "";
        } else {
            return d1 + "";
        }
    }

}
