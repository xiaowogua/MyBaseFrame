package cn.ccrise.genki.mvp.startup;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wxl on 2017/10/12.
 */
@Module
public class StartUpModule {
    @Provides
    StartUpContract.Presenter providerPresenter(){
        return new StartUpPresenter();
    }
}
