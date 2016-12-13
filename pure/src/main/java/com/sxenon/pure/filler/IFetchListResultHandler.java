package com.sxenon.pure.filler;

import com.sxenon.pure.core.IResultHandler;

import java.util.List;

/**
 * ResultHandler for fetching list data
 * Created by Sui on 2016/12/8.
 */

public interface IFetchListResultHandler<T> extends IResultHandler {
    void onListDataFetched(List<T> data);
}