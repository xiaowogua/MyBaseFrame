package cn.ccrise.baseframe.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import butterknife.ButterKnife;
import cn.ccrise.baseframe.utils.LoadingDialogUtil;
import cn.ccrise.baseframe.utils.MLog;
import cn.ccrise.baseframe.widget.SwipeBackLayout;


/**
 * Created by wxl on 2017/10/10.
 */

public abstract class BaseActivity<T extends IBasePresenter> extends AppCompatActivity implements IBaseView {

    protected Toolbar toolbar;
    protected TextView toolbar_title;
    protected View toolbar_bg;
    protected View toolbar_status;
    protected LinearLayout ll_base_view;
    protected View toolbar_line;
    protected ImageView toolbar_back;

    protected ActionBar actionBar;

    protected FrameLayout content_view;

    protected Handler handler;

    private final boolean NOT_PAY = true;

    protected Activity mContext;
    /**
     * 不收键盘的view
     */
    protected Set<View> notHideKeyboardSet;

    /**
     * 通用的requestCode
     */
    public static final int REQUEST_CODE_COMMON = 1024;
    /**
     * 通用的setResult时data的key
     */
    public static final String RESULT_KEY = "result";

    @Inject
    protected T presenter;

    Dialog loadingDialog;

    @Nullable
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        loadingDialog = LoadingDialogUtil.getLoadingDialog(mContext);

        if (registerEventBus()) {
            EventBus.getDefault().register(this);
        }
        superSetContentView(cn.ccrise.baseframe.R.layout.my_base_layout);
        if (isSlidingRightBack()) {
            SwipeBackLayout sbl = new SwipeBackLayout(this);
            sbl.attachToActivity(this);
        }

        toolbar = (Toolbar) findViewById(cn.ccrise.baseframe.R.id.toolbar);
        content_view = (FrameLayout) findViewById(cn.ccrise.baseframe.R.id.content_view);
        toolbar_title = (TextView) findViewById(cn.ccrise.baseframe.R.id.toolbar_title);
        toolbar_bg = findViewById(cn.ccrise.baseframe.R.id.toolbar_bg);
        toolbar_status = findViewById(cn.ccrise.baseframe.R.id.toolbar_status);
        ll_base_view = findViewById(cn.ccrise.baseframe.R.id.ll_base_view);
        toolbar_line = findViewById(cn.ccrise.baseframe.R.id.toolbar_line);
        toolbar_back = (ImageView) findViewById(cn.ccrise.baseframe.R.id.toolbar_back);

        toolbar_back.setOnClickListener((view) -> {
            onBackPressed();
        });

        notHideKeyboardSet = new HashSet<>();

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        if (isImmersiveMode()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }

            toolbar_status.getLayoutParams().height = BaseApplication.statusHeaigh;
        }

        handler = BaseApplication.getAppComponent().getHandler();

        setTitleText(getTitle());

        int contentViewId = getContentViewId();
        if (contentViewId > 0) {
            setContentView(contentViewId);
        }
        ButterKnife.bind(this);
        MLog.d("butterKnife", "ButterKnife bind");
        inject();

        if (presenter != null) {
            presenter.bind(this);
        }

        init();
    }

    /**
     * 获取布局文件
     *
     * @return 布局文件ID
     */
    protected abstract @LayoutRes
    int getContentViewId();

    /**
     * dagger 注入 Presenter
     */
    protected abstract void inject();

    /**
     * 初始化内容
     */
    protected abstract void init();

    @Override
    protected void onDestroy() {
        if (registerEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        if (presenter != null) {
            presenter.unBind();
        }
        super.onDestroy();
    }

    @Override
    public void finish() {
        hideInput();
        super.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(mContext);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        content_view.removeAllViews();
        LayoutInflater.from(this).inflate(layoutResID, content_view, true);
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        content_view.removeAllViews();
        if (params == null) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        content_view.addView(view, params);
//        if(NOT_PAY){
//            ImageView mark = new ImageView(this);
//            mark.setImageResource(((BaseApplication) BaseApplication.getInstance()).getMark());
//            mark.setAlpha(0.618f);
//            FrameLayout.LayoutParams markParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            markParams.gravity = Gravity.CENTER;
//            content_view.addView(mark, markParams);
//        }
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        content_view.addView(view, params);
    }

    protected void setTitleText(int titleRes) {
        setTitleText(getText(titleRes));
    }

    protected void setTitleText(CharSequence titleText) {
        toolbar_title.setText(titleText);
    }

    /**
     * 是否右滑返回
     *
     * @return
     */
    protected boolean isSlidingRightBack() {
        return false;
    }

    /**
     * 是否是沉浸式
     *
     * @return
     */
    protected boolean isImmersiveMode() {
        return true;
    }

    /**
     * 是否注册到EventBus
     *
     * @return
     */
    protected boolean registerEventBus() {
        return false;
    }

    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    @Override
    public void dismissLoading() {
        loadingDialog.dismiss();
    }

    protected InputMethodManager inputMethodManager;

    public void hideInput() {
        if (inputMethodManager == null) {
            inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        }
        if (getCurrentFocus() != null) {
            try {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showInput(final View view) {
        if (view == null) {
            return;
        }
        view.requestFocus();
        if (view instanceof EditText) {
            ((EditText) view).setSelection(((EditText) view).length());
        }
        if (inputMethodManager == null) {
            inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        }
        inputMethodManager.showSoftInput(view, 0);
    }

    /**
     * 自动收键盘
     */
    @Override
    public boolean dispatchTouchEvent(final MotionEvent ev) {
        if (autoHideInput()) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                final View v = getCurrentFocus();
                if (isShouldHideInput(v, ev)) {
                    hideInput();
                }
                return super.dispatchTouchEvent(ev);
            }
            // 必不可少，否则所有的组件都不会有TouchEvent了
            if (getWindow().superDispatchTouchEvent(ev)) {
                return true;
            }
            return onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    protected boolean autoHideInput() {
        return true;
    }

    /**
     * @param v
     * @param event
     * @return
     * @author wangxl
     * @date 2016-6-7  上午11:14:39
     * @class BaseActivity.java
     * @description 判断是否该收键盘
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        int[] leftTopToolbar = {0, 0};
        toolbar_bg.getLocationInWindow(leftTopToolbar);

        for (View view : notHideKeyboardSet) {
            if (isEventInView(view, event)) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            }
        }
        if (v != null && (v instanceof EditText)) {
            if (isEventInView(v, event)) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private boolean isEventInView(@NonNull View v, MotionEvent event) {
        int[] leftTopToolbar = {0, 0};
        toolbar_bg.getLocationInWindow(leftTopToolbar);

        int[] leftTop = {0, 0};
        //获取输入框当前的location位置
        v.getLocationInWindow(leftTop);
        int left = leftTop[0];
        int top = leftTop[1] + (leftTopToolbar[1] < 0 ? -leftTopToolbar[1] : 0);
        int bottom = top + v.getHeight() + (leftTopToolbar[1] < 0 ? -leftTopToolbar[1] : 0);
        int right = left + v.getWidth();
        float eventX = event.getX();
        float eventY = event.getY();
        if (eventX > left && eventX < right
                && eventY > top && eventY < bottom) {
            // 点击的是输入框区域，保留点击EditText的事件
            return true;
        }
        return false;
    }

    public void superSetContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    public void superSetContentView(View view) {
        super.setContentView(view);
    }

    @Override
    public void onBackPressed() {
        if (mOnBackPressedListener != null && mOnBackPressedListener.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    public interface OnBackPressedListener {
        /**
         * 按下返回键
         *
         * @return true：拦截事件
         */
        boolean onBackPressed();
    }

    /**
     * 返回键监听
     */
    OnBackPressedListener mOnBackPressedListener;

    public void setOnBackPressedListener(OnBackPressedListener listener) {
        mOnBackPressedListener = listener;
    }
}
