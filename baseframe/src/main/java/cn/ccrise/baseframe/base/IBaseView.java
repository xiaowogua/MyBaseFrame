package cn.ccrise.baseframe.base;

import android.content.Context;
import android.support.annotation.Nullable;

/**
 * Created by wxl on 2017/10/12.
 */

public interface IBaseView {
    @Nullable Context getContext();

    void finish();

    /**
     * 显示加载动画
     */
    default void showLoading(){};

    /**
     * 取消加载动画
     */
    default void dismissLoading(){};
}
