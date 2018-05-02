package cn.ccrise.baseframe.http;

/**
 * 网络请求回调
 * Created by wxl on 2017/10/16.
 */

public interface HttpCallBack<T> {
    /**
     * 网络请求回调方法
     *
     * @param result 返回值
     */
    void onComplete(HttpResult<T> result);
}
