package cn.ccrise.baseframe.base;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.squareup.leakcanary.LeakCanary;

import cn.ccrise.baseframe.BuildConfig;
import cn.ccrise.baseframe.dagger2.ApiModule;
import cn.ccrise.baseframe.dagger2.AppComponent;
import cn.ccrise.baseframe.dagger2.DaggerAppComponent;
import cn.ccrise.baseframe.utils.ToastUtil;

/**
 * Created by wxl on 2017/10/10.
 */

public class BaseApplication extends Application {
    private static BaseApplication instance;
    /**
     * 是否为DEBUG
     */
    public static boolean DEBUG = BuildConfig.DEBUG;
    /**
     * 是否为模拟器
     */
    public static boolean emulator;
    /**
     * 版本号
     */
    private static int version;
    /**
     * 版本名
     */
    private static String versionName;
    /**
     * 状态栏高度
     */
    public static int statusHeaigh;

    private static ApplicationInfo applicationInfo;

    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        instance = this;

        try {
            DEBUG = "DEBUG".equals(getMetaData("BUILD_TYPE"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        version = 1;
        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (Exception e) {
        }
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (Exception e) {
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object object = clazz.newInstance();
                int height = Integer.parseInt(clazz.getField("status_bar_height")
                        .get(object).toString());
                statusHeaigh = getResources().getDimensionPixelSize(height);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            statusHeaigh = 0;
        }

        String model = Build.MODEL;
        emulator = "Android SDK built for x86".equalsIgnoreCase(model);

        appComponent = DaggerAppComponent.builder().apiModule(new ApiModule()).build();

        ToastUtil.init(this);
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public static String getMetaData(String key){
        if(applicationInfo == null){
            try {
                applicationInfo = getInstance().getPackageManager().getApplicationInfo(getInstance().getPackageName(), PackageManager.GET_META_DATA);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return applicationInfo.metaData.getString(key);
    }

    public static int getVersion() {
        return version;
    }

    public static String getVersionName() {
        return versionName;
    }

    public static int getId(String name, String type) {
        return instance.getResources().getIdentifier(name, type, instance.getPackageName());
    }

    public static AppComponent getAppComponent() {
        return instance.appComponent;
    }
}
