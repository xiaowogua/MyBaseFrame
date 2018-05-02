package cn.ccrise.baseframe.http;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.TimeUnit;

import cn.ccrise.baseframe.base.BaseApplication;
import cn.ccrise.baseframe.base.IBasePresenter;
import cn.ccrise.baseframe.utils.MLog;
import cn.ccrise.baseframe.utils.ToastUtil;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wxl on 2017/10/13.
 */

public class BaseRequest<T> {
    public static final String ERROR_CODE = "500";
    public static final String ERROR_MSG = "网络异常，请重新尝试";

    private IBasePresenter basePresenter;

    private boolean lock = false;

    Handler handler;

    public BaseRequest(IBasePresenter basePresenter) {
        this.basePresenter = basePresenter;
        handler = new Handler(Looper.getMainLooper());
    }

    protected T create(Class<T> cls){
        return BaseApplication.getAppComponent().getRetrofit().create(cls);
    }

    protected <K> void post(Observable<HttpResult<K>> observable, HttpCallBack<K> callBack){
        post(observable, callBack, HttpConfig.getDefaultConfig());
    }

    protected <K> void post(Observable<HttpResult<K>> observable, HttpCallBack<K> callBack, HttpConfig config){
        if(config.lock){
            if(lock) {
                return;
            }else{
                lock = true;
            }
        }
        postSimple(observable, callBack, config);
    }
    private <K> void postSimple(Observable<HttpResult<K>> observable, HttpCallBack<K> callBack, HttpConfig config){
        MLog.d("rx_debug", "start");
        MLog.e("http_debug", config.toString());
        if(config.showDialog){
            show();
        }
        observable.subscribeOn(Schedulers.io()).delay(5, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpObserver<K>(callBack, config));
    }

    class HttpObserver<T> implements Observer<HttpResult<T>> {
        HttpConfig config;
        HttpCallBack<T> callBack;

        public HttpObserver(HttpCallBack<T> callBack, HttpConfig config) {
            this.config = config;
            this.callBack = callBack;
        }

        @Override
        public void onSubscribe(@NonNull Disposable d) {
            MLog.d("rx_debug", "on sub");
        }

        @Override
        public void onNext(@NonNull HttpResult<T> result) {
            MLog.d("rx_debug", "on next " + result.toString());
            if(config.cancelDialog){
                if(basePresenter != null){
                    handler.post(()->{
                        basePresenter.dismiss();
                    });
                }
            }
            if(!result.isSuccess()){
                if(config.toastMessage){
                    handler.post(()->{
                        ToastUtil.toast(result.msg);
                    });
                }
            }

            if(config.lock){
                lock = false;
            }
            callBack.onComplete(result);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            if(config.cancelDialog || config.cancelDialogOnError){
                dismiss();
            }
            HttpResult<T> result = new HttpResult<T>(ERROR_CODE, ERROR_MSG);
            onNext(result);
            MLog.e("rx_debug", "on error", e);
        }

        @Override
        public void onComplete() {
            MLog.d("rx_debug", "on complete");
        }
    }

    public void dismiss(){
        if(basePresenter != null){
            basePresenter.dismiss();
        }
    }

    public void show(){
        MLog.d("http_debug", "show");
        if(basePresenter != null){
            MLog.i("http_debug", "show");
            handler.post(()->{
                basePresenter.show();
            });
        }
    }
}
