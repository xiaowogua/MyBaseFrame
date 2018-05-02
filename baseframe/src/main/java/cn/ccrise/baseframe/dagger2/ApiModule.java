package cn.ccrise.baseframe.dagger2;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import cn.ccrise.baseframe.base.BaseApplication;
import cn.ccrise.baseframe.utils.ConstantProvider;
import cn.ccrise.baseframe.utils.MLog;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by wxl on 2017/10/12.
 */
@Module
public class ApiModule {
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        final int timeout = 60;
        HttpLoggingInterceptor.Level level = BaseApplication.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(level);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(new HeaderInterceptor())
                .build();
        return client;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client, Gson gson) {
        MLog.d("rx_debug", "BASE_URL = " + ConstantProvider.getInstance().getBaseUrl());
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(ConstantProvider.getInstance().getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    Gson provideGson(){
        return new GsonBuilder().setDateFormat(ConstantProvider.getInstance().getDefaultDateFormat()).create();
    }

    @Provides
    @Singleton
    EventBus provideEventBus(){
        return new EventBus();
    }

    class HeaderInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .header("Content-Type", "application/json;charset=utf-8")
                    .header("Accept", "application/json;charset=utf-8")
                    .header("auth_token", ConstantProvider.getInstance().getToken())
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        }
    }

    @Provides
    @Singleton
    Handler provideHandler(){
        return new Handler(Looper.getMainLooper());
    }
}
