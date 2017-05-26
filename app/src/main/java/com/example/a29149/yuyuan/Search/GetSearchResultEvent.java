package com.example.a29149.yuyuan.Search;

/**
 * Created by 张丽华 on 2017/5/6.
 * Description:
 */

public class GetSearchResultEvent {
    private String condition;
    private boolean result;
    private String keyValue;

    public GetSearchResultEvent(String condition, boolean result, String keyValue) {
        this.condition = condition;
        this.result = result;
        this.keyValue = keyValue;
    }



    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "GetSearchResultEvent{" +
                "condition='" + condition + '\'' +
                ", result=" + result +
                ", keyValue='" + keyValue + '\'' +
                '}';
    }
}
