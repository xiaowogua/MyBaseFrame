package cn.ccrise.baseframe.base;

/**
 * Created by wxl on 2017/10/12.
 */

public interface IBasePresenter<T extends IBaseView> {
    /**
     * 绑定View
     * @param view
     */
    void bind(T view);

    /**
     * 解除绑定
     */
    void unBind();

    void show();

    void dismiss();
}
