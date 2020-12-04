package com.example.callapp


import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {


    private val CHANNEL_ID = "channel_id"
    public var notificationId = 101
    private val time = 0 //in milliseconds
    private val mNotificationTime = Calendar.getInstance().timeInMillis + time //Set after 5 seconds from the current time.
//    public var notificationId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


//        savedInstanceState?.run {
//            notificationId = savedInstanceState.getInt(MainActivity.KEY_COUNT)
//        }

        createNotificationChannel()

        button2.setOnClickListener {
            sendNotification()
        }

    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH//
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(){

        val landingIntent = Intent(this, test::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
//        landingIntent.flags = FLAG_ACTIVITY_NEW_TASK
//        landingIntent.flags = FLAG_ACTIVITY_CLEAR_TASK

//        intent.putExtra("fromNotification", "book_ride")
//        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)

//        val intentConfirm = Intent(this, NotificationActionReceiver::class.java)
//        intentConfirm.action = "CONFIRM"
//        intentConfirm.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
//
//
//        val intentCancel = Intent(this, NotificationActionReceiver::class.java)
//        intentCancel.action = "CANCEL"
//        intentCancel.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
//
////This Intent will be called when Notification will be clicked by user.
//
////This Intent will be called when Notification will be clicked by user.
        val pendingIntent = PendingIntent.getActivity(this, 0, landingIntent, 0)
//
////This Intent will be called when Confirm button from notification will be
////clicked by user.
//
////This Intent will be called when Confirm button from notification will be
////clicked by user.
//        val pendingIntentConfirm =
//            PendingIntent.getBroadcast(this, 0, intentConfirm, PendingIntent.FLAG_CANCEL_CURRENT)
//
////This Intent will be called when Cancel button from notification will be
////clicked by user.
//
////This Intent will be called when Cancel button from notification will be
////clicked by user.
//        val pendingIntentCancel = PendingIntent.getBroadcast(this, 1, intentCancel, PendingIntent.FLAG_CANCEL_CURRENT)

//        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


//        val yesintent = Intent(this, YesActivity::class.java)
//        yesintent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//
//        val yespendingintent = PendingIntent.getActivity(this, 0, yesintent, PendingIntent.FLAG_CANCEL_CURRENT)
//
//        val nointent = Intent(this, NoActivity::class.java)
//        nointent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        val nopendingintent = PendingIntent.getActivity(this, 0, nointent, 0)

//        val snoozeIntent = Intent(this, test::class.java).apply {
//            action = ACTION_CAMERA_BUTTON
//            putExtra(EXTRA_NOTIFICATION_ID, 0)
//        }
        val callnowIntent = Intent(this, test::class.java).apply {
            identifier = "now"
//            action = Context.ALARM_SERVICE
        }
//        callnowIntent.identifier = "now"
        val callnowPendingIntent: PendingIntent = PendingIntent.getBroadcast(this, 0, callnowIntent, 0)

        val snoozeIntent = Intent(this, test::class.java).apply {
            identifier = "later"
        }
        val snoozePendingIntent: PendingIntent = PendingIntent.getBroadcast(this, 0, snoozeIntent, 0)

        val tomorrowIntent = Intent(this, test::class.java).apply {
            identifier = "tomorrow"
        }
        val tomorrowPendingIntent: PendingIntent = PendingIntent.getBroadcast(this, 0, tomorrowIntent, 0)



        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("AHHHHHHHHHHHHHHHHHHHHHH")
            .setContentText("help me :(")
//            .setAutoCancel(true)
//            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_launcher_foreground, "Call Now", callnowPendingIntent)
            .addAction(R.drawable.ic_launcher_foreground, "Snooze for 2 hours", snoozePendingIntent)
            .addAction(R.drawable.ic_launcher_foreground, "Call Tomorrow", tomorrowPendingIntent)
            .setAutoCancel(true)

//        notificationBuilder.addAction(R.drawable.ic_launcher_background, "Yes", yespendingintent)
//        notificationBuilder.addAction(R.drawable.ic_launcher_background, "NO", nopendingintent)


//        notificationBuilder.addAction(R.drawable.ic_launcher_foreground, "Confirm", pendingIntentConfirm)
//        notificationBuilder.addAction(R.drawable.ic_launcher_foreground, "Cancel", pendingIntentCancel)


//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        with(NotificationManagerCompat.from(this)) {
//            // notificationId is a unique int for each notification that you must define
//            notificationId++;
            notify(notificationId, notificationBuilder.build())
        }

//        notificationBuilder.notify(notificationId /* ID of notification */, notificationBuilder.build())


//        val intent = Intent(this, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
//
////        val snoozeIntent = Intent(this, MyBroadcastReceiver::class.java).apply {
////            action = ACTION_SNOOZE
////            putExtra(EXTRA_NOTIFICATION_ID, 0)
////        }
////      val snoozePendingIntent: PendingIntent = PendingIntent.getBroadcast(this, 0, snoozeIntent, 0)
//
//        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle("My notification")
//            .setContentText("Hello World!")
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            // Set the intent that will fire when the user taps the notification
//            .setContentIntent(pendingIntent)
////           .addAction(R.drawable.ic_snooze, getString(R.string.snooze), snoozePendingIntent)
//            .setAutoCancel(true)
//
//        val intentCallNow = Intent(this, NotificationActionReceiver::class.java).apply {
////            action = ACTION_CAMERA_BUTTON
////            putExtra(EXTRA_ALARM_COUNT)
////            action = SNOOZE_ALARM
//            action = "call now"
//            putExtra(EXTRA_NOTIFICATION_ID, 0)
//        }
////        val pendingIntentConfirm = PendingIntent.getBroadcast(this, 0, intentConfirm, PendingIntent.FLAG_CANCEL_CURRENT);
//        val pIntent = PendingIntent.getBroadcast(this, 1, intentCallNow, 0)
//        builder.addAction(R.drawable.ic_launcher_background, "Call Now", pIntent)
//
//
//
//        val intentSnooze1 = Intent("action.snooze1")
//        val pending1 = PendingIntent.getBroadcast(this, 1, intentSnooze1, 0)
//        builder.addAction(R.drawable.ic_launcher_background, "Snooze for 2 hours", pending1)
//
//        val intentSnooze2 = Intent("action.name")
//        val pending2 = PendingIntent.getBroadcast(this, 1, intentSnooze2, 0)
//        builder.addAction(R.drawable.ic_launcher_background, "Call Tomorrow", pending2)
//
//        with(NotificationManagerCompat.from(this)) {
//            // notificationId is a unique int for each notification that you must define
//            notify(notificationId, builder.build())
//        }
//        val calendar: Calendar = Calendar.getInstance()
//        setAlarm(calendar.timeInMillis)
    }

//    fun setNotification(timeInMilliSeconds: Long, activity: Activity) {
//
//        //------------  alarm settings start  -----------------//
//
//        if (timeInMilliSeconds > 0) {
//
//
//            val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
//            val alarmIntent = Intent(activity.applicationContext, AlarmReceiver::class.java) // AlarmReceiver1 = broadcast receiver
//
//            alarmIntent.putExtra("reason", "notification")
//            alarmIntent.putExtra("timestamp", timeInMilliSeconds)
//
//
//            val calendar = Calendar.getInstance()
//            calendar.timeInMillis = timeInMilliSeconds
//
//
//            val pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)
//            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
//
//        }
//
//        //------------ end of alarm settings  -----------------//
//
//
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setAlarm(timeInMillis: Long) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, MyAlarm::class.java)

        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager.setRepeating(AlarmManager.RTC, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show()
    }
    private class MyAlarm : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            Log.d("tag", "Alarm just fired")
        }
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putInt(KEY_COUNT, notificationId)
//    }

//    companion object {
//
//        // Notification ID to allow for future updates
//        const val MY_NOTIFICATION_ID = 1
//
//        private const val KEY_COUNT = "key_count"
//
//        // Notification Text Elements
////        private const val mTickerText = "This is a Really, Really, Super Long Notification Message!"
////        private const val mContentText = "You've Been Notified!"
//
//        // Notification Sound and Vibration on Arrival
////        private val soundURI = Uri
////            .parse("android.resource://course.examples.notification.statusbarwithcustomview/" + R.raw.alarm_rooster)
////        private val mVibratePattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
//
//        // Notification Channel ID
////        private lateinit var mChannelID: String
//
////        private lateinit var mNotificationManager: NotificationManager
//    }
}

//adb pull "/sdcard/DCIM/Camera/IMG_20201125_003922.jpg" "C:\Users\Katerina\Pictures\Pixel Camera\Test"