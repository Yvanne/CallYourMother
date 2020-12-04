package com.example.callapp;

//package course.examples.notification.statusbarwithcustomview

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.RemoteViews


public class Notif : Activity() {


    companion object {

        // Notification ID to allow for future updates
        const val MY_NOTIFICATION_ID = 1

        private const val KEY_COUNT = "key_count"

        // Notification Text Elements
        private const val mTickerText = "This is a Really, Really, Super Long Notification Message!"
        private const val mContentText = "You've Been Notified!"

        // Notification Sound and Vibration on Arrival
//        private val soundURI = Uri
//            .parse("android.resource://course.examples.notification.statusbarwithcustomview/" + R.raw.alarm_rooster)
        private val mVibratePattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

        // Notification Channel ID
        private lateinit var mChannelID: String

        private lateinit var mNotificationManager: NotificationManager
    }

    // Notification Count
    private var mNotificationCount: Int = 0


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        createNotificationChannel()

    }

    private fun createNotificationChannel() {

        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        mChannelID = "$packageName.channel_01"
        // The user-visible name of the channel.
        val name = getString(R.string.channel_name)

        // The user-visible description of the channel.
        val description = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val mChannel = NotificationChannel(mChannelID, name, importance)

        // Configure the notification channel.
        mChannel.description = description
        mChannel.enableLights(true)

        // Sets the notification light color for notifications posted to this
        // channel, if the device supports this feature.
        mChannel.lightColor = Color.RED
        mChannel.enableVibration(true)
        mChannel.vibrationPattern = mVibratePattern

//        mChannel.setSound(
//            soundURI,
//            AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build()
//        )

        mNotificationManager.createNotificationChannel(mChannel)



    }


    fun onClick(v: View) {

        // Define action Intent
        val notificationIntent = Intent(applicationContext, NotificationSubActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val contentIntent = PendingIntent.getActivity(
            applicationContext, 0,
            notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val contentView = RemoteViews(
            packageName,
            R.layout.fragment_second
        )

        contentView.setTextViewText(
//            R.id.notification_text,
            mNotificationCount,
            "$mContentText ( ${++mNotificationCount} )"
        )

//        val snoozeIntent = Intent(this, MyBroadcastReceiver::class.java).apply {
//            action = ACTION_SNOOZE
//            putExtra(EXTRA_NOTIFICATION_ID, 0)
//        }
//        val snoozePendingIntent: PendingIntent =
//            PendingIntent.getBroadcast(this, 0, snoozeIntent, 0)
//        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setSmallIcon(R.drawable.notification_icon)
//            .setContentTitle("My notification")
//            .setContentText("Hello World!")
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setContentIntent(pendingIntent)
//            .addAction(R.drawable.ic_snooze, getString(R.string.snooze), snoozePendingIntent)

        ///////////////////////////////////////////////////////////

        val intent = Intent(this, NotificationSubActivity::class.java);
        intent.putExtra("fromNotification", "book_ride");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

//        Intent().also { intent ->
//            intent.setAction("com.example.broadcast.MY_NOTIFICATION")
//            intent.putExtra("data", "Notice me senpai!")
//            sendBroadcast(intent)
//        }

        val intentConfirm = Intent(this, NotificationActionReceiver::class.java);
        intentConfirm.action = "CONFIRM";
        intentConfirm.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        val intentCancel = Intent(this, NotificationActionReceiver::class.java);
        intentCancel.setAction("CANCEL");
        intentCancel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

//This Intent will be called when Notification will be clicked by user.
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

//This Intent will be called when Confirm button from notification will be
//clicked by user.
        val pendingIntentConfirm = PendingIntent.getBroadcast(this, 0, intentConfirm, PendingIntent.FLAG_CANCEL_CURRENT);

//This Intent will be called when Cancel button from notification will be
//clicked by user.
        val pendingIntentCancel = PendingIntent.getBroadcast(this, 1, intentCancel, PendingIntent.FLAG_CANCEL_CURRENT);

//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


//        NotificationCompat.Builder notificationBuilder = NotificationCompat.Builder(this)
//            .setSmallIcon(R.drawable.logo_steer)
//            .setContentTitle("Madaar Ride")
//            .setContentText("" + messageBody)
//            .setAutoCancel(true)
//            .setSound(defaultSoundUri)
//            .setContentIntent(pendingIntent);
//
//        notificationBuilder.addAction(R.drawable.ic_check_black, "Confirm", pendingIntentConfirm);
//        notificationBuilder.addAction(R.drawable.ic_clear_black, "Cancel", pendingIntentCancel);
//
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//
//        notificationManager.notify(11111 /* ID of notification */, notificationBuilder.build());

//        // Define the Notification's expanded message and Intent:
        val notificationBuilder = Notification.Builder(applicationContext, mChannelID)
            .setTicker(mTickerText)
            .setSmallIcon(android.R.drawable.stat_sys_warning)
            .setAutoCancel(true)
//            .setContentIntent(contentIntent)
//            .setCustomContentView(contentView)
//            .addAction(R.drawable.ic_snooze, getString(R.string.snooze), snoozePendingIntent)
            .setContentIntent(pendingIntent);

        val intentt = Intent("action.name")
//        val pendingIntentConfirm = PendingIntent.getBroadcast(this, 0, intentConfirm, PendingIntent.FLAG_CANCEL_CURRENT);
        val pIntent = PendingIntent.getBroadcast(this, 1, intentt, 0)
        notificationBuilder.addAction(R.drawable.ic_launcher_background, "Action button name", pIntent)

//        notificationBuilder.addAction(R.drawable.ic_launcher_background, "Confirm", pendingIntentConfirm);
//        notificationBuilder.addAction(R.drawable.ic_launcher_foreground, "Cancel", pendingIntentCancel);


//

//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Pass the Notification to the NotificationManager:
        mNotificationManager.notify(MY_NOTIFICATION_ID, notificationBuilder.build())


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_COUNT, mNotificationCount)
    }
}