package tiny.populardemo.model.login;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
import tiny.populardemo.BuildConfig;

/**
 * 登录模拟服务器
 * 创建者:tiny
 * 日期:17/3/2
 */
public class LoginInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;
        if (BuildConfig.DEBUG) {
            String result = "{\n" +
                    "    \"code\": 10,\n" +
                    "    \"msg\": \"123\",\n" +
                    "    \"name\": \"lisi\",\n" +
                    "    \"age\": 22\n" +
                    "}";
            response = new Response.Builder()
                    .code(200)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_0)
                    .body(ResponseBody.create(MediaType.parse("application/json"), result.getBytes()))
                    .addHeader("content-type", "application/json")
                    .build();
        } else {
            response = chain.proceed(chain.request());
        }
        return response;
    }
}
