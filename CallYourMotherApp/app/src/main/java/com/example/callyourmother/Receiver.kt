package com.example.callyourmother

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import java.util.*


//private val Context.ALARM_SERVICE: Context
//    get() {}

class Receiver  : BroadcastReceiver() {
    //    private var mAlarmManager: AlarmManager? = null
    private var time = 0.toLong()//in ms
    private val curtime = Calendar.getInstance().timeInMillis
//    private  val curtime = System.currentTimeMillis()
var yourMain: MainActivity? = null

    fun setMainActivityHandler(main: MainActivity) {
        yourMain = main
    }



    override fun onReceive(context: Context, intent: Intent) {

//        val broadcastObserver = BroadcastObserver()
//        broadcastObserver.triggerObservers()

//        val service = Intent(context, NotificationService::class.java) //
//        context.startService(service)


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



//            val i = Intent(Intent.ACTION_CALL);
////            intent = Intent(Intent.ACTION_CALL)
//            i.data = Uri.parse("tel:4444444444")
//            context.startActivity(i)
//            context.sendBroadcast(intent)

//            yourMain?.phonecall()
//            val launchIntent: Intent = getPackageManager().getLaunchIntentForPackage("com.google.android.youtube")
//            val launchIntent: Intent = context.packageManager.getLaunchIntentForPackage("com.google.android.youtube")
//            val launchIntent = Intent(this, context.packageManager.getLaunchIntentForPackage("com.google.android.youtube"))
//
//            if (launchIntent != null) {
//                startActivity(launchIntent)
//            } else {
//                Toast.makeText(
//                    context,
//                    "There is no package available in android",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//            val intent: Intent? = context.packageManager.getLaunchIntentForPackage("com.package.youtube")
////                GetPackageManager().getLaunchIntentForPackage("com.example.rushiActivity")
////            var intent = PackageManager.GetLaunchIntentForPackage("com.package.y");
//            startActivity(intent);
//            val callIntent: Intent = Uri.parse("tel:4444444").let { number ->
//                Intent(Intent.ACTION_DIAL, number)
//            }
//
//            if (ActivityCompat.checkSelfPermission(MainActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
//
//            } else {
//                ActivityCompat.requestPermissions(MainActivity(), arrayOf(Manifest.permission.CALL_PHONE), 112)
//            }

//            val man = MainActivity()
//            man.phonecall()
//            if (ActivityCompat.checkSelfPermission(MainActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(MainActivity(), Array(1) { Manifest.permission.CALL_PHONE }, 112)///
//            } else {
//            //has perm
//            phonecall()
//            val intent = Intent(Intent.ACTION_CALL);
//            intent.data = Uri.parse("tel:4444444444")
//            startActivity(intent)
////        }

//            intent.data = Uri.parse("tel:$number")
//            MainActivity().broadcastIntent(view = View.inflate())


            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(MainActivity().notificationId)
        }
        if(intent.identifier == "later"){
            Log.i("tag","clicked snooze");
            Toast.makeText(context, "Reminder Snoozed", Toast.LENGTH_LONG).show()//

            time = (System.currentTimeMillis() + 7200000)
            val d : Long = 2000
//            val callnowIntent = Intent(this, ).apply {
//                identifier = "now"
////            action = Intent.ACTION_CALL
//            }
//            sendNotification(d)



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
//        var isConnected = true
//        broadcastResult(isConnected)
    }



   //    protected abstract fun broadcastResult(connected: Boolean)

}

