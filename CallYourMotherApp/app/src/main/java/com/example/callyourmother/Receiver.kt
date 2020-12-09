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


        val int = Intent("broadCastName")
        val st = intent.identifier.toString()
        int.putExtra("identifier", st)
        context.sendBroadcast(int)

        /*the identifier is how the Broadcast Receiver can identify which notification action the user selects,
        between 'Call Now' 'Snooze for 2 hours' and 'Call Tomorrow' */
        if(intent.identifier == "now"){

            //dismisses notification upon selection
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(MainActivity().notificationId)
        }
        if(intent.identifier == "later"){

            //dismisses notification upon selection
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(MainActivity().notificationId)
        }

        if(intent.identifier == "tomorrow"){

            //dismisses notification upon selection
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(MainActivity().notificationId)
        }

    }

}
