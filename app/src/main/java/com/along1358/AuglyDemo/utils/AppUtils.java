package com.along1358.AuglyDemo.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class AppUtils {
    private static Application application;
    private static final ActivityLifecycleImpl activityLifecycle = new ActivityLifecycleImpl();
    private static long quitCnt = 0;

    private AppUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 初始化AppUtils
     *
     * @param app
     */
    public static void init(Application app) {
        if (application != null) return;
        application = app;
        application.registerActivityLifecycleCallbacks(activityLifecycle);
    }

    /**
     * 获取app
     *
     * @return
     */
    @SuppressLint("PrivateApi")
    public static Application getApp() {
        if (application != null)
            return application;
        else {
            try {
                Class<?> aClass = Class.forName("android.app.ActivityThread");
                Object currentActivityThread = aClass.getMethod("currentActivityThread").invoke((Object) null);
                Object getApplication = aClass.getMethod("getApplication").invoke(currentActivityThread);
                //在非主线程中调用
                if (getApplication == null)
                    throw new NullPointerException("u should init first");
                //在activity线程中调用
                init((Application) getApplication);
                return application;
            } catch (Exception e) {
                e.printStackTrace();
            }

            throw new NullPointerException("u should init first");
        }
    }

    /**
     * 退出app
     */
    public static void exitApp() {
        finishAllActivity();
        System.exit(0);
    }

    /**
     * 获取顶层Context
     *
     * @return
     */
    public static Context getTopActivityOrApp() {
        Activity activity = activityLifecycle.getTopActivity();
        return activity == null ? getApp() : activity;
    }

    private static void finishAllActivity() {
        Iterator<Activity> iterator = activityLifecycle.mActivityList.iterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();
            if (next != null)
                next.finish();
        }
    }

    static class ActivityLifecycleImpl implements Application.ActivityLifecycleCallbacks {
        final LinkedList<Activity> mActivityList = new LinkedList();

        public ActivityLifecycleImpl() {
        }

        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
            this.setTopActivity(activity);
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {
            this.setTopActivity(activity);
        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {
            this.setTopActivity(activity);
        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {

        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
            this.mActivityList.remove(activity);
        }

        private void setTopActivity(Activity activity) {
            if (this.mActivityList.contains(activity)) {
                if (!((Activity) this.mActivityList.getLast()).equals(activity)) {
                    this.mActivityList.remove(activity);
                    this.mActivityList.addLast(activity);
                }
            } else {
                this.mActivityList.addLast(activity);
            }

        }

        Activity getTopActivity() {
            if (!this.mActivityList.isEmpty()) {
                Activity topActivity = (Activity) this.mActivityList.getLast();
                if (topActivity != null) {
                    return topActivity;
                }
            }

            try {
                Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
                Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke((Object) null);
                Field activitiesField = activityThreadClass.getDeclaredField("mActivityList");
                activitiesField.setAccessible(true);
                Map activities = (Map) activitiesField.get(activityThread);
                if (activities == null) {
                    return null;
                }

                Iterator var5 = activities.values().iterator();

                while (var5.hasNext()) {
                    Object activityRecord = var5.next();
                    Class activityRecordClass = activityRecord.getClass();
                    Field pausedField = activityRecordClass.getDeclaredField("paused");
                    pausedField.setAccessible(true);
                    if (!pausedField.getBoolean(activityRecord)) {
                        Field activityField = activityRecordClass.getDeclaredField("activity");
                        activityField.setAccessible(true);
                        Activity activity = (Activity) activityField.get(activityRecord);
                        this.setTopActivity(activity);
                        return activity;
                    }
                }
            } catch (ClassNotFoundException var11) {
                var11.printStackTrace();
            } catch (IllegalAccessException var12) {
                var12.printStackTrace();
            } catch (InvocationTargetException var13) {
                var13.printStackTrace();
            } catch (NoSuchMethodException var14) {
                var14.printStackTrace();
            } catch (NoSuchFieldException var15) {
                var15.printStackTrace();
            }
            return null;
        }
    }

    public static void askQuit() {
        if (application == null) return;
        if (System.currentTimeMillis() - quitCnt > 2000) {
            Toast.makeText(application, "再按一次退出", Toast.LENGTH_SHORT).show();
            quitCnt = System.currentTimeMillis();
        } else {
            exitApp();
        }
    }

    public static void installApp(String apkPath) {
        Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(application, application.getPackageName() + ".fileProvider", new File(apkPath));
        } else {
            uri = Uri.fromFile(new File(apkPath));
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        application.startActivity(intent);
    }

}
