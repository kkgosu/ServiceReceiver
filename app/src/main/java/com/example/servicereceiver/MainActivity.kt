package com.example.servicereceiver

import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mSimpleReceiver: SimpleReceiver
    private lateinit var mIntentFilter: IntentFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startServiceBtn.setOnClickListener {
            val intent = Intent(this, CountService::class.java)
            startService(intent)
        }

        stopServiceBtn.setOnClickListener {
            val intent = Intent(this, CountService::class.java)
            stopService(intent)
        }

        sendBroadcastBtn.setOnClickListener {
            sendBroadcast(Intent(SimpleReceiver.SIMPLE_ACTION))
        }

        mIntentFilter = IntentFilter(SimpleReceiver.SIMPLE_ACTION)
        mSimpleReceiver = SimpleReceiver(timeTextView)

    }

    override fun onResume() {
        super.onResume()
        registerReceiver(mSimpleReceiver,mIntentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mSimpleReceiver)
    }
}
