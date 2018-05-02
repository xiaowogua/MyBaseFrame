package cn.ccrise.baseframe.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ccrise.baseframe.utils.LoadingDialogUtil;
import cn.ccrise.baseframe.utils.MLog;

/**
 * Created by wxl on 2017/10/10.
 */

public abstract class BaseFragment<T extends IBasePresenter> extends Fragment implements IBaseView {
    protected Context mContext;
    @Inject
    protected T presenter;

    Dialog loadingDialog;

    protected Unbinder unbinder;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;

        loadingDialog = LoadingDialogUtil.getLoadingDialog(mContext);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MLog.d("fragment_debug", "onCreateView");
        View rootView = inflater.inflate(getContentViewId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        inject();

        if (presenter != null) {
            presenter.bind(this);
        }

        init();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(presenter != null){
            presenter.unBind();
        }
        unbinder.unbind();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    @Override
    public void dismissLoading() {
        loadingDialog.dismiss();
    }

    /**
     * 获取布局文件
     *
     * @return 布局文件ID
     */
    protected abstract @LayoutRes int getContentViewId();
    /**
     * dagger 注入 Presenter
     */
    protected abstract void inject();

    /**
     * 初始化内容
     */
    protected abstract void init();
}
