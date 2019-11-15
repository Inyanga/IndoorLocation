package com.test.indoorlocation.foreground_service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.test.indoorlocation.ble.startScan


class ForegroundBleService : Service() {
    val serviceId = 13666

    override fun onBind(intent: Intent?) = null


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("DEBUG_FOREGROUND", "Foreground service started")
        startForeground(serviceId, createNotification(this))
        startScan()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("DEBUG_FOREGROUND", "Foreground service stopped")
        stopForeground(true)
    }
}