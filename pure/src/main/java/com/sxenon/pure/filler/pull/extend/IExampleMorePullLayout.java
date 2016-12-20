package com.sxenon.pure.filler.pull.extend;

import com.sxenon.pure.filler.pull.IBasePullLayout;

/**
 * Created by Sui on 2016/12/19.
 */

public interface IExampleMorePullLayout extends IBasePullLayout {
    void doMoreFunc();

    void setMoreDelegate(ExampleMorePullDelegate moreDelegate);

    interface ExampleMorePullDelegate {
        void onDoMoreFunc();
    }
}