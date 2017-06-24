package com.example.a29149.yuyuan.AbstractObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.R;

import java.util.List;

/**
 *
 * Author:       geyao
 * Date:         2017/6/15
 * Email:        gy2016@mail.ustc.edu.cn
 * Description:  用来做一个抽象类，需求场景是对用户头像进行 头像 + 昵称 的填充
 *               它所展示的数据，有studentDTO即可
 */

public abstract class UserPhotoAdapter<T extends AbstractDTO> extends YYBaseAdapter<T>{

    //图片加载器
    protected RequestManager glide;

    /**
     * @param dataList 数据
     * @param context  上下文
     *
     * @discription 构造函数
     * @author 29149
     * @time 2017/6/10 15:12
     */
    public UserPhotoAdapter(List<T> dataList, Context context) {
        super(dataList, context);
    }

    /**
     * 构造函数
     *
     * @param mContext 上下文
     */
    public UserPhotoAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View createView(int position, View convertView, LayoutInflater layoutInflater) {
        PhotoViewHolder photoViewHolder = null;
        if (convertView == null)
        {
            convertView  = View.inflate(mContext, R.layout.gridview_reply_item_layout, null);
            photoViewHolder = new PhotoViewHolder(convertView);
            convertView.setTag(photoViewHolder);
        }
        else
        {
            photoViewHolder = (PhotoViewHolder) convertView.getTag();
        }

        //子类填充数据，并放编写逻辑
        setUI(photoViewHolder, position);
        return convertView;
    }

    /**
     *
     * Author:       geyao
     * Date:         2017/6/15
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  子类来给昵称、头像赋值，或者编写逻辑
     * @param photoViewHolder ViewHolder
     * @param position 位置
     */
    protected abstract void setUI(PhotoViewHolder photoViewHolder, int position);

    /**
     *
     * Author:       geyao
     * Date:         2017/6/15
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  ViewHolder提升效率
     */
    public static class PhotoViewHolder
    {

        public ImageView mPhoto;
        public TextView mNickedName;
        public TextView mPrestige;

        public PhotoViewHolder(View view)
        {
            mPhoto = (ImageView) view.findViewById(R.id.iv_photo);
            mNickedName = (TextView) view.findViewById(R.id.tv_teacherName);
            mPrestige = (TextView) view.findViewById(R.id.tv_prestige);
        }
    }
}

