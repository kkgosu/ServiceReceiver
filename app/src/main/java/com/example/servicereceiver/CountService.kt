package com.example.servicereceiver

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class CountService : Service() {
    private val TAG = "CountService"
    private lateinit var mScheduleExecutorService: ScheduledExecutorService

    override fun onBind(intent: Intent): IBinder? {
        return null //because onBind is for bounded services
    }

    override fun onCreate() {
        Log.d(TAG, "onCreate: ")
        mScheduleExecutorService = Executors.newScheduledThreadPool(1)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ")

        mScheduleExecutorService.scheduleAtFixedRate({
            val timeMillis = System.currentTimeMillis()
            val intentToSend = Intent(SimpleReceiver.SIMPLE_ACTION)
            intentToSend.putExtra("Time", timeMillis)
            sendBroadcast(intentToSend)
        }, 0, 4, TimeUnit.SECONDS)

        return START_STICKY //if service was killed bt the system, it will be recreated asap
    }


    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        mScheduleExecutorService.shutdownNow()
    }
}
