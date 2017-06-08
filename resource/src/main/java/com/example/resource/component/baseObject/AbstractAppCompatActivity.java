package com.example.resource.component.baseObject;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.resource.DataHelper;
import com.example.resource.util.AsyncHttpClient;
import com.example.resource.util.HttpMethod;

/**
 * Created by 张丽华 on 2017/5/19.
 * Description:
 */

public abstract class AbstractAppCompatActivity extends AppCompatActivity {

    public void SS() {
        new AsyncHttpClient(this, "URL", HttpMethod.POST)
                .setOnHttpResultListener(new AsyncHttpClient.OnHttpResultListener() {
                    @Override
                    public void onSuccess(String result) {
                        new DataHelper<String>().save(result, "JsonName", new DataHelper.Callback() {
                            @Override
                            public void onResult(int resultCode) {
                                switch (resultCode) {
                                    case DataHelper.SUCCESS:

                                        break;
                                    case DataHelper.FAILURE:

                                        break;
                                    case DataHelper.EXCEPTION:

                                        break;
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure() {

                    }
                })
                .setOnResultExceptionListener(new AsyncHttpClient.OnResultExceptionListener() {
                    @Override
                    public void onException(@Nullable Exception e) {

                    }
                })
                .send();
    }
}
