package com.example.servicereceiver

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class CountService : Service() {
    private val TAG = "CountService"
    private lateinit var mScheduleExecutorService: ScheduledExecutorService
    private lateinit var mNotificationManager: NotificationManager
    private lateinit var mNotificationBuilder: NotificationCompat.Builder

    override fun onBind(intent: Intent): IBinder? {
        return null //because onBind is for bounded services
    }

    override fun onCreate() {
        Log.d(TAG, "onCreate: ")
        mScheduleExecutorService = Executors.newScheduledThreadPool(1)

        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationBuilder = getNotificationBuilder()
        mNotificationBuilder.setContentTitle("Count Service Notification")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
    }

    private fun getNotificationBuilder(): NotificationCompat.Builder {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            NotificationCompat.Builder(this)
        } else {
            val channelId = "channelId"
            if (mNotificationManager.notificationChannels == null) {
                val channel = NotificationChannel(
                    channelId, "Text for user"
                    , NotificationManager.IMPORTANCE_LOW
                )
                mNotificationManager.createNotificationChannel(channel)
            }
            NotificationCompat.Builder(this, channelId)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ")

        startForeground(123, getNotification("some texts"))

        mScheduleExecutorService.scheduleAtFixedRate({
            val timeMillis = System.currentTimeMillis()
            mNotificationManager.notify(123, getNotification("time is $timeMillis"))

            val intentToSend = Intent(SimpleReceiver.SIMPLE_ACTION)
            intentToSend.putExtra("Time", timeMillis)
            sendBroadcast(intentToSend)
        }, 0, 4, TimeUnit.SECONDS)

        return START_STICKY //if service was killed bt the system, it will be recreated asap
    }

    private fun getNotification(text: String): Notification? {
        val intent = Intent(this, TempActivity::class.java)
        intent.putExtra("Time", text)
        val pendingIntent = PendingIntent.getActivity(this, 123, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        return mNotificationBuilder.setContentText(text)
            .setContentIntent(pendingIntent)
            .build()
    }


    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        mScheduleExecutorService.shutdownNow()
    }
}
