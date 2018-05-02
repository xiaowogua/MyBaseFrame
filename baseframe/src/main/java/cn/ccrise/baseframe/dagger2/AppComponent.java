package cn.ccrise.baseframe.dagger2;

import android.os.Handler;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by wxl on 2017/10/12.
 */
@Singleton
@Component(modules = {ApiModule.class})
public interface AppComponent {

    OkHttpClient getClient();

    Retrofit getRetrofit();

    Gson getGson();

    EventBus getEventBus();

    Handler getHandler();
}
