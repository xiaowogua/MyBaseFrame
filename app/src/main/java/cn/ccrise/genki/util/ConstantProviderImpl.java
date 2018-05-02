package cn.ccrise.genki.util;

import cn.ccrise.baseframe.utils.IConstantProvider;
import cn.ccrise.genki.http.HttpBase;

/**
 * Created by wxl on 2017/10/13.
 */

public class ConstantProviderImpl implements IConstantProvider {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    @Override
    public String getBaseUrl() {
        return HttpBase.BASE_URL;
    }

    @Override
    public String getDefaultDateFormat() {
        return DATE_FORMAT;
    }

    @Override
    public String getToken() {
        return "";
    }
}
