package cn.ccrise.genki.http;

import cn.ccrise.genki.MyApplication;

import cn.ccrise.genki.MyApplication;

/**
 * 网络请求基础常量
 * Created by wxl on 2017/10/13.
 */

public class HttpBase {
    /**
     * 接口服务器根路径
     */
    public static final String BASE_URL;
    /**
     * 图片服务器根路径
     */
    public static final String BASE_IMAGE;

    /**
     * 测试服务器根路径
     */
    private static final String BASE_URL_TEST = "http://59.46.52.194:8017/FirflyAPI/app/";
    /**
     * 正式服务器根路径
     */
    private static final String BASE_URL_OFFICIAL = "http://59.46.52.194:8017/FirflyAPI/app/";
    /**
     * 测试图片服务器根路径
     */
    private static final String BASE_IMAGE_TEST = "http://img.my.csdn.net/uploads/";
    /**
     * 测试图片服务器根路径
     */
    private static final String BASE_IMAGE_OFFICIAL = "http://img.my.csdn.net/uploads/";

    static {
        String serverType = MyApplication.getMetaData("serverType");
        BASE_URL = "OFFICIAL".equals(serverType) ? BASE_URL_OFFICIAL : BASE_URL_TEST;
        BASE_IMAGE = "OFFICIAL".equals(serverType) ? BASE_IMAGE_OFFICIAL : BASE_IMAGE_TEST;
    }
}
