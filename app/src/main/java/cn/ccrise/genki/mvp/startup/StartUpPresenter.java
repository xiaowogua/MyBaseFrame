package cn.ccrise.genki.mvp.startup;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import cn.ccrise.baseframe.base.BasePresenterImpl;
import cn.ccrise.baseframe.utils.MLog;
import cn.ccrise.genki.http.HttpRequest;
import cn.ccrise.genki.mvp.home.MainActivity;

/**
 * Created by wxl on 2017/10/12.
 */

public class StartUpPresenter extends BasePresenterImpl<StartUpContract.View> implements StartUpContract.Presenter {

    Handler handler;

    HttpRequest request;

    @Override
    protected void init() {
        handler = new Handler(Looper.getMainLooper());
        countDown(3);
        request.getAd(1, result -> {
            MLog.d("http_debug", result.toString());

            handler.post(() -> mView.countDown(3));

        });
    }

    private void countDown(int time) {
        if (time == 0) {
            mView.getContext().startActivity(new Intent(mView.getContext(), MainActivity.class));
            mView.finish();
            return;
        }
        mView.countDown(3);
        handler.postDelayed(() -> {
            int i = time;
            countDown(--i);
        }, 1000L);
    }

}
