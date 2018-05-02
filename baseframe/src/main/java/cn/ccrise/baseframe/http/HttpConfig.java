package cn.ccrise.baseframe.http;

/**
 * 网络请求设置参数
 * Created by wxl on 2017/10/16.
 */

public class HttpConfig {
    /**
     * 是否显示loading弹窗
     */
    boolean showDialog;
    /**
     * 请求结束是否取消弹窗
     */
    boolean cancelDialog;
    /**
     * 是否显示toast，仅当请求失败时显示
     */
    boolean toastMessage;
    /**
     * 失败时是否取消弹窗，仅当{@link #cancelDialog}为false时有效
     */
    boolean cancelDialogOnError;
    /**
     * 当一次请求未结束时是否进行下一个请求
     */
    boolean lock;

    private HttpConfig(boolean showDialog, boolean cancelDialog, boolean toastMessage, boolean cancelDialogOnError, boolean lock) {
        this.showDialog = showDialog;
        this.cancelDialog = cancelDialog;
        this.toastMessage = toastMessage;
        this.cancelDialogOnError = cancelDialogOnError;
        this.lock = lock;
    }

    /**
     * 获取默认设置
     * @return
     */
    public static HttpConfig getDefaultConfig(){
        return new HttpConfig(true, true, true, true, true);
    }

    /**
     * 获取后台请求设置，什么都不显示
     * @return
     */
    public static HttpConfig getBackgroundConfig(){
        return new HttpConfig(false, false, false, false, false);
    }

    public HttpConfig setShowDialog(boolean showDialog) {
        this.showDialog = showDialog;
        return this;
    }

    public HttpConfig setCancelDialog(boolean cancelDialog) {
        this.cancelDialog = cancelDialog;
        return this;
    }

    public HttpConfig setToastMessage(boolean toastMessage) {
        this.toastMessage = toastMessage;
        return this;
    }

    public HttpConfig setCancelDialogOnError(boolean cancelDialogOnError) {
        this.cancelDialogOnError = cancelDialogOnError;
        return this;
    }

    public boolean isLock() {
        return lock;
    }

    public HttpConfig setLock(boolean lock) {
        this.lock = lock;
        return this;
    }

    @Override
    public String toString() {
        return "HttpConfig{" +
                "showDialog=" + showDialog +
                ", cancelDialog=" + cancelDialog +
                ", toastMessage=" + toastMessage +
                ", cancelDialogOnError=" + cancelDialogOnError +
                ", lock=" + lock +
                '}';
    }
}
