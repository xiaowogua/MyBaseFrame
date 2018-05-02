package cn.ccrise.baseframe.base;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.ccrise.baseframe.http.BaseRequest;
import cn.ccrise.baseframe.utils.MLog;

/**
 * Created by wxl on 2017/10/16.
 */

public abstract class BasePresenterImpl<T extends IBaseView> implements IBasePresenter<T> {

    protected T mView;
    Type type;
    Class<? super T> cls;

    private final List<BaseRequest> requestList;

    protected Handler handler;

    public BasePresenterImpl() {
        requestList = new ArrayList<>();
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public final void bind(T view) {
        mView = view;
        MLog.d("type_debug", "type = 222");
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            Class cls = field.getType();
            if (isRequest(cls)) {
                Object object = null;
                try {
                    try {
                        object = cls.getConstructor(IBasePresenter.class).newInstance(this);
                    } catch (NoSuchMethodException e) {
                        object = cls.newInstance();
                    }
                    field.setAccessible(true);
                    field.set(this, object);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                if (object != null && object instanceof BaseRequest) {
                    requestList.add((BaseRequest) object);
                }
            }
        }
        MLog.d("type_debug", "type = 11");
        type = getSuperclassTypeParameter(getClass());
        cls = (Class<? super T>) $Gson$Types.getRawType(type);
        MLog.d("type_debug", "type = " + type.toString());
        MLog.d("type_debug", "class = " + cls.getName());
        init();
    }

    Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
//        return superclass;
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    private boolean isRequest(Class cls) {
        if (cls == null) {
            return false;
        }
        if (cls == Object.class) {
            return false;
        }
        if (cls == BaseRequest.class) {
            return true;
        }
        return isRequest(cls.getSuperclass());
    }

    /**
     * 初始化
     */
    protected void init(){};

    @Override
    public final void unBind() {
        for (BaseRequest request : requestList) {
            request.dismiss();
        }
        mView = (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                MLog.d("type_debug", "动态代理");
                return null;
            }
        });

        handler.removeCallbacksAndMessages(null);

        onUnBind();
    }

    protected void onUnBind() {}

    @Override
    public void show() {
        mView.showLoading();
    }

    @Override
    public void dismiss() {
        mView.dismissLoading();
    }
}
