package cn.ccrise.genki.http;

import cn.ccrise.baseframe.base.IBasePresenter;
import cn.ccrise.baseframe.http.BaseRequest;
import cn.ccrise.baseframe.http.HttpCallBack;
import cn.ccrise.genki.mvp.startup.AdvertisementInfo;

/**
 * Created by wxl on 2017/10/13.
 */

public class HttpRequest extends BaseRequest<IRequest> {
    public HttpRequest(IBasePresenter basePresenter) {
        super(basePresenter);
    }

    public void getAd(int type, HttpCallBack<AdvertisementInfo> callBack) {
        IRequest request = create(IRequest.class);
        post(request.getAd(type), callBack);
    }
}
