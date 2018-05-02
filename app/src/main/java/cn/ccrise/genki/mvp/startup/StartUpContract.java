package cn.ccrise.genki.mvp.startup;

import cn.ccrise.baseframe.base.IBasePresenter;
import cn.ccrise.baseframe.base.IBaseView;
import cn.ccrise.baseframe.dagger2.ActivityScope;
import cn.ccrise.baseframe.dagger2.AppComponent;

import dagger.Component;

/**
 * Created by wxl on 2017/10/12.
 */

public interface StartUpContract {
    interface Presenter extends IBasePresenter<View> {
    }

    interface View extends IBaseView {
        /**
         * 显示启动页广告
         *
         * @param filePath 图片路径
         */
        void showImage(String filePath);

        /**
         * 倒计时
         *
         * @param second 剩余多少秒跳转
         */
        void countDown(int second);
    }

    /**
     * Created by wxl on 2017/10/12.
     */
    @ActivityScope
    @Component(modules = StartUpModule.class, dependencies = AppComponent.class)
    interface IComponent {
        void inject(StartUpActivity activity);
        void inject(StartUpPresenter presenter);
    }
}
