package cn.ccrise.baseframe.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * 加载Dialog工具类
 * Created by wxl on 2017/10/16.
 */

public class LoadingDialogUtil {

    private static LoadingDialogFactory mFactory;

    public static Dialog getLoadingDialog(Context context){
        if(mFactory == null){
            return new ProgressDialog(context);
        }
        return mFactory.getLoadingDialog(context);
    }

    public interface LoadingDialogFactory{
        Dialog getLoadingDialog(Context context);
    }

    public static void setFactory(LoadingDialogFactory factory){
        mFactory = factory;
    }
}
