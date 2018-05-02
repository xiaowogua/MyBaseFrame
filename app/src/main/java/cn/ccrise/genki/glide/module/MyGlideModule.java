package cn.ccrise.genki.glide.module;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.InputStream;

import cn.ccrise.genki.http.HttpBase;

/**
 * Glide配置
 * Created by wxl on 2017/10/13.
 */
@GlideModule
public class MyGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //显示高质量图片
        builder.setDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888).disallowHardwareConfig());
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        //拼接图片路径
        registry.prepend(String.class, InputStream.class, new UrlLoaderFactory());
    }

    class UrlLoaderFactory implements ModelLoaderFactory<String, InputStream>{

        @Override
        public UrlLoader build(MultiModelLoaderFactory multiFactory) {
            return new UrlLoader(multiFactory.build(String.class, InputStream.class));
        }

        @Override
        public void teardown() {}

        class UrlLoader implements ModelLoader<String, InputStream>{
            ModelLoader<String, InputStream> modelLoader;

            public UrlLoader(ModelLoader<String, InputStream> modelLoader) {
                this.modelLoader = modelLoader;
            }

            @Nullable
            @Override
            public LoadData<InputStream> buildLoadData(String s, int width, int height, Options options) {
                return modelLoader.buildLoadData(HttpBase.BASE_IMAGE + s, width, height, options);
            }

            @Override
            public boolean handles(String s) {
                return !(TextUtils.isEmpty(s) || s.contains("://") || new File(s).exists());
            }
        }
    }
}
