package com.example.a29149.yuyuan.AbstractObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * Created by 张丽华 on 2017/6/10.
 * Description:
 */

public class TestAdapter extends YYBaseAdapter<String> {
    public TestAdapter(List<String> data, Context context) {
        super(data, context);
    }

    @Override
    public View createView(int position, View convertView, LayoutInflater layoutInflater) {
        return null;
    }
}
