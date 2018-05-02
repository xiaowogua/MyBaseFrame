package cn.ccrise.genki.mvp.startup;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ccrise.baseframe.base.BaseActivity;
import cn.ccrise.genki.MyApplication;
import okhttp3.OkHttpClient;

/**
 * Created by wxl on 2017/10/12.
 */

public class StartUpActivity extends BaseActivity<StartUpContract.Presenter> implements StartUpContract.View {
    @BindView(cn.ccrise.genki.R.id.iv_ad)
    ImageView ivAd;
    @BindView(cn.ccrise.genki.R.id.tv_second)
    TextView tvSecond;

    @Inject
    OkHttpClient client;

    @Override
    public void showImage(String filePath) {
        ivAd.setImageURI(Uri.parse(filePath));
    }

    @Override
    public void countDown(int second) {
        tvSecond.setText(second + "秒后跳转");
    }

    @Override
    protected int getContentViewId() {
        return cn.ccrise.genki.R.layout.activity_start_up;
    }

    @Override
    protected void inject() {
        StartUpContract.IComponent component = DaggerStartUpContract_IComponent.builder().appComponent(MyApplication.getAppComponent()).startUpModule(new StartUpModule()).build();
        component.inject(this);
    }

    @Override
    protected void init() {
        toolbar_bg.setVisibility(View.GONE);
    }

    @Override
    protected boolean isImmersiveMode() {
        return true;
    }
}
