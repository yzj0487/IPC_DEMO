package com.solide.server

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.solide.ipcdemo.Communication

/**
 * Created by Solide on 2017/10/19.
 */
class ComService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.i("test", "ComService onCreate")
    }


    override fun onBind(p0: Intent?): IBinder {
        return myBinder
    }

    private val myBinder: Communication.Stub = object : Communication.Stub() {
        override fun sendMsg(msg: String?) {
            Log.i("test", msg)
        }
    }
}