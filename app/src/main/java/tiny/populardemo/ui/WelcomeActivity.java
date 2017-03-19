package tiny.populardemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Button;

import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import tiny.populardemo.R;
import tiny.populardemo.base.BaseActivity;
import tiny.populardemo.manager.ActivityManager;

/**
 * 欢迎界面
 * 创建者:tiny
 * 日期:17/3/19
 */

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.btn_kill)
    Button btn_kill;

    public static void start(Activity source) {
        Intent intent = new Intent(source, WelcomeActivity.class);
        ActivityCompat.startActivity(source, intent, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        RxView.clicks(btn_kill).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                ActivityManager.getInstance().finishActivity(MainActivity.class);
            }
        });
    }
}
