package cn.ccrise.baseframe.http;

import com.google.gson.annotations.SerializedName;

import cn.ccrise.baseframe.base.BaseApplication;

/**
 * 网络请求返回值
 * Created by wxl on 2017/10/13.
 */

public class HttpResult<T> {
    @SerializedName("status")
    String status;
    @SerializedName("msg")
    String msg;
    @SerializedName("data")
    T data;

    public HttpResult() {
    }

    public HttpResult(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public HttpResult(String status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    @Override
    public String toString() {
        return BaseApplication.getAppComponent().getGson().toJson(this);
    }

    public boolean isSuccess(){
        return "200".equals(status);
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
