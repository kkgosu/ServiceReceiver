package com.example.servicereceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class SimpleReceiver : BroadcastReceiver() {

    companion object {
        val SIMPLE_ACTION = "com.example.servicereceiver.SIMPLE_ACTION"
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        Toast.makeText(context, intent!!.action, Toast.LENGTH_SHORT).show()
    }
}