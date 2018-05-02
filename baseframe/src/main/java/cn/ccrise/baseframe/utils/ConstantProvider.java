package cn.ccrise.baseframe.utils;

/**
 * Created by wxl on 2017/10/13.
 */

public class ConstantProvider implements IConstantProvider {

    private static ConstantProvider instance = new ConstantProvider();
    private IConstantProvider mProvicer;

    private ConstantProvider() {
    }

    public static ConstantProvider getInstance(){
        return instance;
    }

    public static void init(IConstantProvider mProvicer){
        instance.mProvicer = mProvicer;
    }

    @Override
    public String getBaseUrl() {
        if(mProvicer == null){
            throw new RuntimeException("You need init ConstantProvider before use it");
        }
        return mProvicer.getBaseUrl();
    }

    @Override
    public String getDefaultDateFormat() {
        if(mProvicer == null){
            throw new RuntimeException("You should init before use");
        }
        return mProvicer.getDefaultDateFormat();
    }

    @Override
    public String getToken() {
        if(mProvicer == null){
            throw new RuntimeException("You should init before use");
        }
        return mProvicer.getToken();
    }
}
