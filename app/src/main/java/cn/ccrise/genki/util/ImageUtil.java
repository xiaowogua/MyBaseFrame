package cn.ccrise.genki.util;

import android.widget.ImageView;

import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import cn.ccrise.genki.glide.module.GlideApp;

import static cn.ccrise.genki.util.Tools.dp2px;

/**
 * 加载图片工具类
 * Created by wxl on 2017/10/13.
 */

public class ImageUtil {
    /**
     * 加载普通图片
     *
     * @param imageView
     * @param url
     */
    public static void loadImage(ImageView imageView, String url) {
        GlideApp.with(imageView).load(url).into(imageView);
    }

    /**
     * 加载头像
     *
     * @param imageView
     * @param url
     */
    public static void loadHead(ImageView imageView, String url) {
        GlideApp.with(imageView).load(url).circleCrop().into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param imageView
     * @param url
     */
    public static void loadRoundCorner(ImageView imageView, String url) {
        loadRoundCorner(imageView, url, 10);
    }

    /**
     * 加载圆角图片
     *
     * @param imageView
     * @param url
     * @param radius    圆角半径(dp)
     */
    public static void loadRoundCorner(ImageView imageView, String url, int radius) {
        loadRoundCornerPx(imageView, url, Tools.dp2px(radius));
    }

    /**
     * 加载圆角图片
     *
     * @param imageView
     * @param url
     * @param radius    圆角半径(px)
     */
    public static void loadRoundCornerPx(ImageView imageView, String url, int radius) {
        GlideApp.with(imageView).load(url).transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners(radius))).into(imageView);
    }
}
