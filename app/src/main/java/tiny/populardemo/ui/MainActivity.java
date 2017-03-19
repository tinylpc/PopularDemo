package tiny.populardemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import tiny.populardemo.R;
import tiny.populardemo.base.BaseActivity;
import tiny.populardemo.model.login.ILoginService;
import tiny.populardemo.model.login.LoginInterceptor;
import tiny.populardemo.ui.login.bean.LoginUser;
import tiny.populardemo.utils.HttpUtils;

/**
 * 首页
 * 创建者:tiny
 * 日期:17/3/19
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.rl)
    RelativeLayout rl;

    @BindView(R.id.btn_to_main)
    Button btn_to_main;

    @BindView(R.id.btn_to_welcome)
    Button btn_to_welcome;

    public static void start(Activity source) {
        Intent intent = new Intent(source, MainActivity.class);
        ActivityCompat.startActivity(source, intent, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        RxView.clicks(btn_to_main).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                MainActivity.start(MainActivity.this);
            }
        });

        RxView.clicks(btn_to_welcome).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                WelcomeActivity.start(MainActivity.this);
            }
        });

        final OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(new LoginInterceptor()).build();

        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://www.tiny.com/")
                .client(client)
                .build();

        String result = "{\n" +
                "    \"name\": \"lpc\",\n" +
                "    \"age\": 27\n" +
                "}";
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), result);
        ILoginService loginService = retrofit.create(ILoginService.class);
        Flowable<LoginUser> l = loginService.login(body);

        HttpUtils.getInstance().sendRequest(l).subscribe(new Consumer<LoginUser>() {
            @Override
            public void accept(LoginUser loginUser) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                Log.i("lpc", "onComplete");
            }
        });
    }
}
