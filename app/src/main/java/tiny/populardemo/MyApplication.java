package tiny.populardemo;

import android.app.Application;

import tiny.populardemo.utils.HttpUtils;

/**
 * 类描述
 * 创建者:tiny
 * 日期:17/3/19
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpUtils.getInstance().init(getApplicationContext());
    }
}
