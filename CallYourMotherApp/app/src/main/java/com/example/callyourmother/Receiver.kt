package com.example.callyourmother

import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_CLOSE_SYSTEM_DIALOGS
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.coroutines.flow.combineTransform
import java.util.*

//private val Context.ALARM_SERVICE: Context
//    get() {}

class Receiver  : BroadcastReceiver() {
    //    private var mAlarmManager: AlarmManager? = null
    private var time = 0.toLong()//in ms
    private val curtime = Calendar.getInstance().timeInMillis
//    private  val curtime = System.currentTimeMillis()

    override fun onReceive(context: Context, intent: Intent) {

//            if (intent.action.equals("yes", ignoreCase = true)) {
//
//                Toast.makeText(context, "call tomorrow", Toast.LENGTH_LONG).show()
//                Log.i("tag","receive");
//
//
//            } else if (intent.action.equals("call tomorrow", ignoreCase = true)) {
//                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//                notificationManager.cancel(101)//////////////////////////
//            }
//            if (intent.action.equals())

//            Log.i("tag","receive");
//            Toast.makeText(context, "received", Toast.LENGTH_LONG).show()//

        if(intent.identifier == "now"){
            Log.i("tag","clicked call now");
            Toast.makeText(context, "calling", Toast.LENGTH_LONG).show()//



            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(MainActivity().notificationId)
        }
        if(intent.identifier == "later"){
            Log.i("tag","clicked snooze");
            Toast.makeText(context, "Reminder Snoozed", Toast.LENGTH_LONG).show()//

            time = (System.currentTimeMillis() + 7200000)
//                intent.action = ACTION_CLOSE_SYSTEM_DIALOGS

//                mAlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//                MainActivity().setNotification(mNotificationTime, this@MainActivity)




            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(MainActivity().notificationId)
        }


        if(intent.identifier == "tomorrow"){
            Log.i("tag","clicked tomorrow");
            Toast.makeText(context, "Snoozed for 24 hours.", Toast.LENGTH_LONG).show()//

            time = (System.currentTimeMillis() + 86400000)


//                val alarmManager = getSystemService(Activity.ALARM_SERVICE) as AlarmManager
//                val alarmIntent = Intent(this, test::class.java) // AlarmReceiver1 = broadcast receiver
//                val pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)
//                alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

//                notificationManager.notificationDelegate

            notificationManager.cancel(MainActivity().notificationId)
        }



//            if (intent.action.equals("call now")) {
////
//                Toast.makeText(context, "call tomorrow", Toast.LENGTH_LONG).show()
//                Log.i("tag","uuuuuuuuuuuuuuh");
////
//
//            val service = Intent(context, NotificationService::class.java)
//            service.putExtra("reason", intent.getStringExtra("reason"))
//            service.putExtra("timestamp", intent.getLongExtra("timestamp", 0))
//
//            service.data = Uri.parse("custom://" + System.currentTimeMillis())
//            context.startService(service)

    }

}

