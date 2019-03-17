package com.example.servicereceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.TextView
import android.widget.Toast
import java.lang.StringBuilder
import java.lang.ref.WeakReference

class SimpleReceiver() : BroadcastReceiver() {

    private lateinit var mWeakReference: WeakReference<TextView>

    constructor(textView: TextView) : this() {
        mWeakReference = WeakReference(textView)
    }


    companion object {
        val SIMPLE_ACTION = "com.example.servicereceiver.SIMPLE_ACTION"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val time = intent?.getLongExtra("Time", 0)
        Toast.makeText(context, time.toString(), Toast.LENGTH_SHORT).show()

        val textView = mWeakReference.get()
        val builder = StringBuilder(textView!!.text)
        builder.append(time).append("\n")
        textView.text = builder.toString()

//        val intentl = Intent(context, TempActivity::class.java)
//        intentl.putExtra("Time", time)
//        context?.startActivity(intentl)
    }
}