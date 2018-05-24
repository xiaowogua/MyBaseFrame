package cn.ccrise.baseframe.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by wxl on 2017/10/16.
 */

public class ToastUtil {
    private static Toast mToast;
    private static Handler handler;
    private static Context context;

    public static void init(Context context) {
        ToastUtil.context = context;
//        toast = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.CENTER, 0, 0);
        handler = new Handler(Looper.getMainLooper());
    }

    public static void toast(int msgId) {
        Toast toast = Toast.makeText(context.getApplicationContext(), msgId, Toast.LENGTH_LONG);
        show(toast);
    }

    public static void toast(CharSequence msg) {
        Toast toast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG);
        show(toast);
    }

    private static void show(Toast toast) {
        if(mToast != null){
            mToast.cancel();
        }
        mToast = toast;
        handler.post(() -> {
            mToast.show();
        });
    }
}
