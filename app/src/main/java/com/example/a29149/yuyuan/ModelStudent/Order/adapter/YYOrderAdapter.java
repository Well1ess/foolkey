package com.example.a29149.yuyuan.ModelStudent.Order.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.AbstractObject.YYBaseAdapter;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsStudentDTO;
import com.example.a29149.yuyuan.Enum.RoleEnum;
import com.example.a29149.yuyuan.ModelStudent.Me.info.StudentInfoActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.resource.util.image.GlideCircleTransform;

import java.util.List;

/**
 * 订单的adapter基类
 * Created by geyao on 2017/6/11.
 */

public abstract class YYOrderAdapter extends YYBaseAdapter<OrderBuyCourseAsStudentDTO> {
    private static final String TAG = "YYOrderAdapter";

    //默认按钮不显示
    protected boolean buttonVisibleFlag = false;
    //默认按钮文字为 ""
    protected String buttonText = "";
    //图片加载器
    private RequestManager glide;

    //构造器
    //构造的时候就指明按钮的显示
    public YYOrderAdapter(List<OrderBuyCourseAsStudentDTO> data, Context context, boolean buttonVisibleFlag, String buttonText) {
        super(data, context);
        this.buttonVisibleFlag = buttonVisibleFlag;
        this.buttonText = buttonText;
    }

    @Override
    public View createView(final int position, View convertView, LayoutInflater layoutInflater) {
        OrderViewHolder viewHolder;
        //使用ViewHolder加优化展示效率
        if (convertView == null) {
            //统一绑定一个布局文件
            convertView = View.inflate(mContext, R.layout.adapter_order, null);
            //设置了按钮的展示
            viewHolder = new OrderViewHolder(convertView, buttonVisibleFlag, buttonText);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (OrderViewHolder) convertView.getTag();
        }
        //拿取数据
        OrderBuyCourseAsStudentDTO orderBuyCourseAsStudentDTO = getItem(position);

        //填充数据
        initView(orderBuyCourseAsStudentDTO, viewHolder);

        //设置点击监听事件
        //头像点击
        viewHolder.mPhoto.setOnClickListener(new View.OnClickListener() {
            //再次拿取数据
            private OrderBuyCourseAsStudentDTO orderBuyCourseAsStudentDTO = getItem(position);
            @Override
            public void onClick(View v) {
                //查看个人资料的意图
                //如果当前是老师，则查看学生的信息
                if (GlobalUtil.getInstance().getUserRole().equals(RoleEnum.teacher.toString())) {
                    Intent intent = new Intent(mContext, StudentInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("DTO", orderBuyCourseAsStudentDTO.getStudentDTO());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }else {
                    //TODO 没有做专门的老师页面的信息展示
                    Intent intent = new Intent(mContext, StudentInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("DTO", orderBuyCourseAsStudentDTO.getStudentDTO());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            }
        });
        //按钮点击事件
        viewHolder.mButton.setOnClickListener(new View.OnClickListener() {
            //再次拿取数据
            private OrderBuyCourseAsStudentDTO orderBuyCourseAsStudentDTO = getItem(position);
            @Override
            public void onClick(View v) {
                buttonClick(orderBuyCourseAsStudentDTO);
            }
        });

        return convertView;
    }

    /**
     * 需要子类重写的
     * 按钮点击监听事件
     */
    abstract void buttonClick(OrderBuyCourseAsStudentDTO orderBuyCourseAsStudentDTO);

    /**
     * 给ViewHolder填充数据
     * @param orderBuyCourseAsStudentDTO 数据
     * @param viewHolder
     */
    public void initView(OrderBuyCourseAsStudentDTO orderBuyCourseAsStudentDTO, OrderViewHolder viewHolder){
        if (orderBuyCourseAsStudentDTO != null) {
            viewHolder.mNickedName.setText(orderBuyCourseAsStudentDTO.getStudentDTO().getNickedName());
            viewHolder.mTitle.setText(orderBuyCourseAsStudentDTO.getCourse().getTopic());
            viewHolder.mAmount.setText("金额:" + orderBuyCourseAsStudentDTO.getOrderDTO().getAmount());
            //填充头像
            glide = Glide.with(mContext);
            glide.load(PictureInfoBO.getOnlinePhoto(orderBuyCourseAsStudentDTO.getStudentDTO().getUserName()))
                    .transform(new GlideCircleTransform(mContext))
                    .error(R.drawable.photo_placeholder1)
                    .into(viewHolder.mPhoto);
            //按钮的设置，在createView里进行了
        }else {
            //空数据

        }


    }


    /**
     * 展示订单的viewHolder
     */
    public static class OrderViewHolder{
        //头像
        ImageView mPhoto;
        //第一行
        TextView mNickedName;
        //第二行
        TextView mTitle;
        //第三行
        TextView mAmount;
        //右边的按钮
        TextView mButton;

        public OrderViewHolder(View view, boolean visible, String text) {
            mPhoto = (ImageView) view.findViewById(R.id.iv_photo);
            mNickedName = (TextView) view.findViewById(R.id.tv_nickedName);
            mTitle = (TextView) view.findViewById( R.id.tv_title );
            mAmount = (TextView) view.findViewById( R.id.tv_amount );
            mButton = (TextView) view.findViewById( R.id.tv_button );

            //根据需要对按钮进行不同的展示
            if (visible) {
                mButton.setVisibility(View.VISIBLE);
            }else {
                mButton.setVisibility(View.INVISIBLE);
            }
            //按钮显示文字
            mButton.setText(text.substring(0, 4));
        }

        @Override
        public String toString() {
            return "OrderViewHolder{" +
                    "mPhoto=" + mPhoto +
                    ", mNickedName=" + mNickedName +
                    ", mTitle=" + mTitle +
                    ", mAmount=" + mAmount +
                    ", mButton=" + mButton +
                    '}';
        }
    }
}
