package com.example.callapp


import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


private const val TAG = "NotificationActionReceiver"

//class NotificationReceiver : BroadcastReceiver() {
//
//    override fun onReceive(context: Context, intent: Intent) {
//        // Build notification based on Intent
//        val notification: Notification = NotificationCompat.Builder(context)
//            .setSmallIcon(R.drawable.ic_launcher_background)
//            .setContentTitle(intent.getStringExtra("title"))
//            .setContentText(intent.getStringExtra("text"))
//            .build()
//        // Show notification
//        val manager =
//            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        manager.notify(42, notification)
//    }
//}
//
//class NotificationActionReceiver : BroadcastReceiver() {
//
//
//    override fun onReceive(context: Context, intent: Intent) {
//
////        StringBuilder().apply {
////            append("Action: ${intent.action}\n")
////            append("URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}\n")
////            toString().also { log ->
////                Log.d(TAG, log)
////                Toast.makeText(context, log, Toast.LENGTH_LONG).show()
////
////            }
////        }
//
//        if (intent.action.equals("action.name", ignoreCase = true)) {
//            Toast.makeText(context, "Booking your ride", Toast.LENGTH_SHORT).show()
//        } else if (intent.action.equals("boot_completed", ignoreCase = true)) {
//            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.cancel(Notif.MY_NOTIFICATION_ID)
//        }
//    }
//
//
//}


class NotificationActionReceiver : BroadcastReceiver() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_first)
//    }


    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals("yes", ignoreCase = true)) {
            Toast.makeText(context, "call tomorrow", Toast.LENGTH_SHORT).show()
            Log.i("tag","receive");
        } else if (intent.action.equals("call tomorrow", ignoreCase = true)) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(101)//////////////////////////
        }
    }
}