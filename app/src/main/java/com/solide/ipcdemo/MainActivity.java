package com.solide.ipcdemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
    private Communication communication;
    private Messenger server;

    private Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("hello response here = " + msg.arg1);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.aidl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent();
                mIntent.setAction("com.android.ipc.comservice");
                mIntent.setPackage("com.solide.server");
                bindService(mIntent, serviceConnection, Context.BIND_AUTO_CREATE);
            }
        });

        findViewById(R.id.mes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sIntent = new Intent();
                sIntent.setAction("com.android.ipc.MsgServer");
                sIntent.setPackage("com.solide.server");
                bindService(sIntent, msgServer, Context.BIND_AUTO_CREATE);
            }
        });
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("test", "service connect");
            communication = Communication.Stub.asInterface(iBinder);
            try {
                communication.sendMsg("Hello World!!!");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            communication = null;
        }
    };


    ServiceConnection msgServer = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            server = new Messenger(iBinder);
            Message message = new Message();
            message.arg1 = 1;
            message.replyTo = messenger;
            try {
                server.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            communication = null;
        }
    };

}
