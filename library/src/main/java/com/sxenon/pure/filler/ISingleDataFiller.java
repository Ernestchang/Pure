package com.sxenon.pure.filler;

import com.sxenon.pure.core.IViewComponentGroup;

/**
 * Filler for single data
 * Created by Sui on 2016/12/8.
 */

public interface ISingleDataFiller<T> extends IViewComponentGroup {
    T getValue();

    void onEmpty();
}
