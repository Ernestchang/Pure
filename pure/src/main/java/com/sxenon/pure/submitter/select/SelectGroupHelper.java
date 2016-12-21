package com.sxenon.pure.submitter.select;

import android.support.annotation.NonNull;


import com.sxenon.pure.core.IPureAdapter;

import java.util.WeakHashMap;

/**
 * Created by Sui on 2016/11/18.
 */

public class SelectGroupHelper {
    private static final WeakHashMap<IPureAdapter, ISelectGroup> selectGroupInstances = new WeakHashMap<>();

    static <T> void bindAdapterAndSelectGroup(@NonNull IPureAdapter<T> adapter,@NonNull ISelectGroup<T> selectGroup){
        selectGroupInstances.put(adapter,selectGroup);
    }

    public static <T> ISelectGroup<T> getSelectGroupRelatedWithAdapter(IPureAdapter<T> adapter){
        //noinspection unchecked
        return selectGroupInstances.get(adapter);
    }
}