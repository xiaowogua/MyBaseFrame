package cn.ccrise.baseframe.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by wxl on 2017/10/16.
 */

public class ToastUtil {
    private static Toast toast;
    private static Handler handler;

    public static void init(Context context) {
        toast = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        handler = new Handler(Looper.getMainLooper());
    }

    public static void toast(int msgId) {
        try {
            toast.setText(msgId);
        } catch (Resources.NotFoundException e) {
            toast.setText(String.valueOf(msgId));
        }
        show();
    }

    public static void toast(CharSequence msg) {
        toast.setText(msg);
        show();
    }

    private static void show() {
        handler.post(() -> {
            toast.show();
        });
    }
}
