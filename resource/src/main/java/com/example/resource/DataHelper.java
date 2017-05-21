package com.example.resource;

import android.support.annotation.Nullable;

import com.example.resource.data.GlobalUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by 张丽华 on 2017/5/19.
 * Description:
 */

public class DataHelper<T> {

    public static final int FAILURE = 0;
    public static final int SUCCESS = 1;
    public static final int EXCEPTION = 2;

    public void save(String result, String name, @Nullable Callback callback) {

        int resultCode = FAILURE;
        T target = null;
        try {
            JSONObject jsonObject = new JSONObject(result);

            Type type = new TypeToken<T>() {
            }.getType();
            target = new Gson().fromJson(jsonObject.getString(name), type);
        } catch (JSONException e) {
            resultCode = EXCEPTION;
        }

        Class object = GlobalUtil.getInstance().getClass();
        Field[] fields = object.getDeclaredFields();
        for (Field field : fields) {
            if (target.getClass().getName().equals(field.getType().getName())) {
                try {
                    field.setAccessible(true);
                    field.set(GlobalUtil.getInstance(), target);
                    resultCode = SUCCESS;
                } catch (IllegalAccessException e) {
                    resultCode = EXCEPTION;
                }
            }
        }

        if (callback != null)
            callback.onResult(resultCode);
    }

    public interface Callback {
        void onResult(int resultCode);
    }

}
