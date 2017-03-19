package tiny.populardemo.manager;

import android.app.Activity;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Activity的管理器，单例，不涉及到多线程
 * 创建者:tiny
 * 日期:17/3/19
 */

public class ActivityManager {

    private static ActivityManager am;
    private SparseArray<Activity> activities = new SparseArray<>();
    private HashMap<String, List<Integer>> sameActivities = new HashMap<>();

    private ActivityManager() {

    }

    public static ActivityManager getInstance() {
        if (am == null) {
            am = new ActivityManager();
        }
        return am;
    }

    /**
     * 增加Activity对象
     *
     * @param activity 对象
     */
    public void addActivity(Activity activity) {
        activities.put(activity.hashCode(), activity);

        String activityName = activity.getClass().getSimpleName();
        if (sameActivities.containsKey(activityName)) {
            List<Integer> lists = sameActivities.get(activityName);
            lists.add(activity.hashCode());
        } else {
            List<Integer> lists = new ArrayList<>();
            lists.add(activity.hashCode());
            sameActivities.put(activityName, lists);
        }
    }

    /**
     * 从队列中移除Activity
     *
     * @param activity 要移除的对象
     */
    public void removeActivity(Activity activity) {
        String activityName = activity.getClass().getSimpleName();
        activities.removeAt(activities.indexOfValue(activity));

        if (sameActivities.containsKey(activityName)) {
            List<Integer> lists = sameActivities.get(activityName);
            if (lists.size() == 1) {
                sameActivities.remove(activityName);
            } else {
                lists.remove(Integer.valueOf(activity.hashCode()));
                sameActivities.put(activityName, lists);
            }
        }
    }

    /**
     * 杀死指定的Activity,此Activity可能会有多个实例,一起杀死
     *
     * @param c activity的Class对象
     */
    public void finishActivity(Class c) {
        String activityName = c.getSimpleName();
        if (sameActivities.containsKey(activityName)) {
            List<Integer> hashcodes = sameActivities.get(activityName);
            for (Integer hashcode : hashcodes) {
                Activity activity = activities.get(hashcode, null);
                if (activity != null) {
                    activity.finish();
                }
            }
        }
    }
}
