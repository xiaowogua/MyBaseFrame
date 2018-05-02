package cn.ccrise.baseframe.utils;

import android.support.annotation.IntDef;
import android.util.Log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.ccrise.baseframe.base.BaseApplication;

/**
 *
 * Created by wangxl on 2016/8/15.
 */

public class MLog {
    private static final String TAG = "FIREFLY";
    /**
     * Android's max limit for a log entry is ~4076 bytes,
     * so 4000 bytes is used as chunk size since default charset
     * is UTF-8
     */
    private static final int CHUNK_SIZE = 4000;

    public static void v(String msg){
        v(TAG, msg);
    }
    public static void v(String tag, String msg){
        if(BaseApplication.DEBUG && msg != null){
            print(Log.VERBOSE, tag, msg);
        }
    }
    public static void i(String msg){
        i(TAG, msg);
    }
    public static void i(String tag, String msg){
        if(BaseApplication.DEBUG && msg != null){
            print(Log.INFO, tag, msg);
        }
    }
    public static void d(String msg){
        d(TAG, msg);
    }
    public static void d(String tag, String msg){
        if(BaseApplication.DEBUG && msg != null){
            print(Log.DEBUG, tag, msg);
        }
    }
    public static void w(String msg){
        w(TAG, msg);
    }
    public static void w(String tag, String msg){
        if(BaseApplication.DEBUG && msg != null){
            print(Log.WARN, tag, msg);
        }
    }
    public static void e(String msg){
        e(TAG, msg);
    }
    public static void e(String tag, String msg){
        if(BaseApplication.DEBUG && msg != null){
            print(Log.ERROR, tag, msg);
        }
    }

    private static void print(@PriorityType int priority, String tag, String msg){
        byte[] bytes = msg.getBytes();
        int length = bytes.length;
        for(int i = 0; i < length; i += CHUNK_SIZE){
            int count = Math.min(length - i, CHUNK_SIZE);
            Log.println(priority, tag,  new String(bytes, i, count));
        }
    }

    public static void e(String tag, String msg, Throwable throwable){
        if(BaseApplication.DEBUG && msg != null){
            Log.e(tag, msg, throwable);
        }
    }

    @IntDef({
            Log.VERBOSE,
            Log.DEBUG,
            Log.INFO,
            Log.WARN,
            Log.ERROR,
    })
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    @interface PriorityType {}
}
