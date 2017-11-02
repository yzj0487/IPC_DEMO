package com.solide.server;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

/**
 * Created by Solide on 2017/11/2.
 */

public class MsgServer extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    private Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("hello request msg = " + msg.arg1);
            Message a = Message.obtain(msg);
            a.arg1 = 2;
            try {
                msg.replyTo.send(a);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    });

}
