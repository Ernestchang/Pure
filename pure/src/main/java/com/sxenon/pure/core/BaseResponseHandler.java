package com.sxenon.pure.core;

import com.sxenon.pure.filler.IFetchListResultHandler;
import com.sxenon.pure.filler.IFetchSingleResultHandler;
import com.sxenon.pure.submitter.ISubmitResultHandler;

import java.util.List;

/**
 * BaseResponseHandler
 * Created by Sui on 2016/12/15.
 */

public class BaseResponseHandler<R> implements IResponseHandler<R> {
    private IResultHandler mResultHandler;
    private ResultHandlerType mResultHandlerType = ResultHandlerType.NONE;

    public BaseResponseHandler(IResultHandler resultHandler) {
        mResultHandler = resultHandler;
    }

    public ResultHandlerType getResultHandlerType() {
        if (ResultHandlerType.NONE == mResultHandlerType) {
            if (mResultHandler instanceof ISubmitResultHandler) {
                mResultHandlerType = ResultHandlerType.SUBMIT;
            } else if (mResultHandler instanceof IFetchListResultHandler) {
                mResultHandlerType = ResultHandlerType.FETCH_LIST;
            } else if (mResultHandler instanceof IFetchSingleResultHandler) {
                mResultHandlerType = ResultHandlerType.FETCH_SINGLE;
            } else {
                mResultHandlerType = ResultHandlerType.SIMPLE;
            }
        }
        return mResultHandlerType;
    }

    /**
     * {@link ResultHandlerType#FETCH_LIST}
     */
    @Override
    public void onListDataFetched(List<R> data) {
        //noinspection unchecked
        ((IFetchListResultHandler<R>) mResultHandler).onListDataFetched(data);
    }

    /**
     * {@link ResultHandlerType#FETCH_SINGLE}
     */
    @Override
    public void onSingleDataFetched(R data) {
        //noinspection unchecked
        ((IFetchSingleResultHandler<R>) mResultHandler).onSingleDataFetched(data);
    }

    /**
     * {@link ResultHandlerType#SUBMIT}
     */
    @Override
    public void onSuccess(R result) {
        //noinspection unchecked
        ((ISubmitResultHandler<R>) mResultHandler).onSuccess(result);
    }

    /**
     * {@link ResultHandlerType#SUBMIT}
     */
    @Override
    public void onFail(R result) {
        //noinspection unchecked
        ((ISubmitResultHandler<R>) mResultHandler).onFail(result);
    }

    @Override
    public void onCancel() {
        mResultHandler.onCancel();
    }

    @Override
    public void onException(ApiException exception) {
        mResultHandler.onException(exception);
    }

    public enum ResultHandlerType {
        NONE,
        SIMPLE,
        SUBMIT,
        FETCH_SINGLE,
        FETCH_LIST
    }
}