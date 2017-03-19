package tiny.populardemo.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import tiny.populardemo.base.BaseBean;

/**
 * 类描述
 * 创建者:tiny
 * 日期:17/3/19
 */

public class HttpUtils {

    private static volatile HttpUtils instance;
    private Context context;

    private HttpUtils() {

    }

    public void init(Context context) {
        this.context = context;
    }

    public static HttpUtils getInstance() {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (instance == null) {
                    instance = new HttpUtils();
                }
            }
        }

        return instance;
    }

    /**
     * 发起请求
     *
     * @param request 请求内容
     * @param <T> 实体对象
     * @return 结果
     */
    public <T> Flowable<T> sendRequest(Flowable<T> request) {
        return sendRequest(request, null);
    }

    /**
     * 发起请求
     *
     * @param request 请求内容
     * @param callBack 对返回结果做的默认处理，null的话默认弹Toast，不为null的话则上层 自己处理
     * @param <T> 实体对象
     * @return 结果
     */
    public <T> Flowable<T> sendRequest(Flowable<T> request, final CallBack<T> callBack) {
        return request.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map(new Function<T, T>() {
            @Override
            public T apply(T t) throws Exception {
                BaseBean baseBean = (BaseBean) t;
                if (baseBean.getCode() != 0) {
                    if (null == callBack) {
                        Toast.makeText(context, baseBean.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        callBack.call(t);
                    }
                }
                //拿到一些公共信息做相关处理
                return t;
            }
        }).doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i("lpc", Log.getStackTraceString(throwable));
            }
        }).onErrorResumeNext(new Publisher<T>() {
            @Override
            public void subscribe(Subscriber<? super T> s) {
                //截获所有异常，上层就不需要处理了
                s.onComplete();
            }
        }).onExceptionResumeNext(new Publisher<T>() {
            @Override
            public void subscribe(Subscriber<? super T> s) {
                //截获所有异常，上层就不需要处理了
                s.onComplete();
            }
        });
    }

    public interface CallBack<T> {
        void call(T t);
    }
}
