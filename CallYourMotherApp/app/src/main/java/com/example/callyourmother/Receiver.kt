package com.example.callyourmother

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import java.util.*


class Receiver  : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.i("tag", "entered onReceive")


        val extras = intent.extras
        val i = Intent("broadCastName")
        // Data you need to pass to activity class
        i.putExtra("message", "beeeeeeeeeeeeeeeeeeeeeep")

        val int = Intent("broadCastName")
        val st = intent.identifier.toString()
        int.putExtra("identifier", st)
        context.sendBroadcast(int)


        if(intent.identifier == "now"){


            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(MainActivity().notificationId)
        }
        if(intent.identifier == "later"){

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(MainActivity().notificationId)
        }

        if(intent.identifier == "tomorrow"){

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(MainActivity().notificationId)
        }

    }

}
