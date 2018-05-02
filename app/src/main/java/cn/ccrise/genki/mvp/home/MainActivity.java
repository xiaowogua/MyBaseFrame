package cn.ccrise.genki.mvp.home;

import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import cn.ccrise.baseframe.base.BaseActivity;
import cn.ccrise.baseframe.utils.ImageConstant;
import cn.ccrise.genki.R;
import cn.ccrise.genki.util.ImageUtil;

public class MainActivity extends BaseActivity {
    @BindView(cn.ccrise.genki.R.id.iv_debug)
    ImageView ivDebug;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void inject() {
        ivDebug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void init() {
        ImageUtil.loadRoundCorner(ivDebug, ImageConstant.IMAGE_HEAD);
    }
}
