package tiny.populardemo.model.login;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import tiny.populardemo.ui.login.bean.LoginUser;

/**
 * 登录相关接口
 * 创建者:tiny
 * 日期:17/3/2
 */
public interface ILoginService {
    /**
     * 登录
     *
     * @return 结果
     */
    @POST("user/login.json")
    Flowable<LoginUser> login(@Body RequestBody body);
}
