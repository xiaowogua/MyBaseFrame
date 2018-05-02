package cn.ccrise.genki;

import cn.ccrise.baseframe.base.BaseApplication;
import cn.ccrise.baseframe.utils.ConstantProvider;
import cn.ccrise.genki.util.ConstantProviderImpl;

/**
 * Created by wxl on 2017/10/12.
 */

public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        ConstantProvider.init(new ConstantProviderImpl());
        DEBUG = true;
    }
}
