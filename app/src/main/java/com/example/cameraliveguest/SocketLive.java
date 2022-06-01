package com.example.cameraliveguest;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;

public class SocketLive {
    private String TAG = "hucaihua";
    public interface SocketCallback {
        void callBack(byte[] data);
    }
    private SocketCallback socketCallback;
    public SocketLive(SocketCallback socketCallback ) {
        this.socketCallback = socketCallback;
    }
    public void start() {
        webSocketClient.connect();
    }
    public void close() {
        try {
            webSocketClient.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void sendData(byte[] bytes) {
        if (webSocketClient != null && webSocketClient.isOpen()) {
            webSocketClient.send(bytes);
        }
    }

    private WebSocketClient webSocketClient = new WebSocketClient(URI.create("ws://10.93.137.36:7001")) {
        @Override
        public void onOpen(ServerHandshake handshakedata) {
            Log.i(TAG, "onOpen");
        }

        @Override
        public void onMessage(String message) {
        }

        @Override
        public void onMessage(ByteBuffer bytes) {
            Log.i(TAG, "消息长度  : " + bytes.remaining());
            byte[] buf = new byte[bytes.remaining()];
            bytes.get(buf);
            socketCallback.callBack(buf);
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {

        }

        @Override
        public void onError(Exception ex) {
            ex.printStackTrace();
        }
    };
}
