package cn.ccrise.genki.http;

import cn.ccrise.baseframe.http.HttpResult;

import cn.ccrise.genki.mvp.startup.AdvertisementInfo;
import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wxl on 2017/10/16.
 */

public interface IRequest {
    @POST("advertisementInfo/getAdvertisementInfo")
    Observable<HttpResult<AdvertisementInfo>> getAd(@Query("type") int type);
}
