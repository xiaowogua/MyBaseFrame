package cn.ccrise.baseframe.utils;

/**
 * 提供常量值接口，将配置信息传递到底层框架中
 * Created by wxl on 2017/10/13.
 */

public interface IConstantProvider {
    String getBaseUrl();
    String getDefaultDateFormat();
    String getToken();
}
