package tiny.populardemo.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tiny.populardemo.manager.ActivityManager;

/**
 * 基Activity，所有的Activity都应继承此类
 * 创建者:tiny
 * 日期:17/3/19
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().removeActivity(this);
        super.onDestroy();
    }
}
