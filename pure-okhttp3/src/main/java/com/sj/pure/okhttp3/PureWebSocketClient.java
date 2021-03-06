/*
 * Copyright (c) 2017 sxenon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sj.pure.okhttp3;

import android.util.Log;

import com.sxenon.pure.core.result.IResultDispatcher;
import com.sxenon.pure.core.protocol.websocket.IWebSocketClient;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * PureWebSocketClient
 * Created by Sui on 2016/12/13.
 */

public abstract class PureWebSocketClient<R extends IResultDispatcher> implements IWebSocketClient<R> {
    private final OkHttpClient mClient;
    private Request.Builder mRequestBuilder;
    private WebSocket mWebSocket;
    private IWebSocketClient.ReadyState mReadyState = ReadyState.CLOSED;
    private PureWebSocketListener mWebSocketListener;
    public static final String TAG="PureWebSocketClient";

    public PureWebSocketClient(OkHttpClient client) {
        mClient = client;
    }

    public PureWebSocketClient resetRequestBuilder(Request.Builder builder) {
        mRequestBuilder = builder;
        return this;
    }

    @Override
    public void connect(String url, final R resultDispatcher) {
        mWebSocketListener = new PureWebSocketListener(resultDispatcher);
        mWebSocket = mClient.newWebSocket(mRequestBuilder.url(url).build(), mWebSocketListener);
        mReadyState=ReadyState.CONNECTING;
    }

    @Override
    public boolean closeBySelf() {
        return mWebSocket.close(IWebSocketClient.CLOSE_NORMAL, "A normal closure");
    }

    @Override
    public void cancel() {
        mWebSocket.cancel();
    }

    @Override
    public void reconnect() {
        if (mReadyState==ReadyState.CLOSED){
            mWebSocket = mClient.newWebSocket(mWebSocket.request(), mWebSocketListener);
            mReadyState=ReadyState.CONNECTING;
        }else {
            Log.w(TAG,"Cannot reconnect because status is not right!");
        }
    }

    @Override
    public boolean send(Object data) {
        if (data instanceof String) {
            return mWebSocket.send((String) data);
        } else if (data instanceof ByteString) {
            return mWebSocket.send((ByteString) data);
        } else {
            throw new IllegalArgumentException("OkHttpWebSocketClient can only send String or ByteString");
        }
    }

    public ReadyState getReadyState() {
        return mReadyState;
    }

    private class PureWebSocketListener extends WebSocketListener {
        private final R resultDispatcher;

        PureWebSocketListener(R resultDispatcher) {
            this.resultDispatcher = resultDispatcher;
        }

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            mReadyState = ReadyState.OPEN;
            preParseOnOpen(webSocket, response, resultDispatcher);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            preParseOnMessage(webSocket, text, resultDispatcher);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            preParseOnMessage(webSocket, bytes, resultDispatcher);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            mReadyState = ReadyState.CLOSING;
            preParseOnClosing(webSocket, code, reason, resultDispatcher);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            mReadyState = ReadyState.CLOSED;
            preParseOnClosed(webSocket, code, reason, resultDispatcher);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            mReadyState = ReadyState.CLOSED;
            preParseOnFailure(webSocket, t, response, resultDispatcher);
        }
    }

    protected abstract void preParseOnOpen(WebSocket webSocket, Response response, R resultDispatcher);

    protected abstract void preParseOnMessage(WebSocket webSocket, String text, R resultDispatcher);

    protected abstract void preParseOnMessage(WebSocket webSocket, ByteString bytes, R resultDispatcher);

    protected abstract void preParseOnClosing(WebSocket webSocket, int code, String reason, R resultDispatcher);

    protected abstract void preParseOnClosed(WebSocket webSocket, int code, String reason, R resultDispatcher);

    protected abstract void preParseOnFailure(WebSocket webSocket, Throwable t, Response response, R resultDispatcher);
}
