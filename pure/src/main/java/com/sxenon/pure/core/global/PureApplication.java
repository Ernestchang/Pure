package com.sxenon.pure.core.global;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

/**
 * 做最纯粹的Application二次封装
 * Created by Sui on 2016/11/24.
 */

public abstract class PureApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GlobalContext.INSTANCE.value=this;
        initDebugConfig();
    }

    private void initDebugConfig(){
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        Stetho.initializeWithDefaults(this);
    }
}