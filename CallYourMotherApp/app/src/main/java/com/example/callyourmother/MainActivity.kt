package com.example.callyourmother

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.SearchView

import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    //test!!!!
    var cols = listOf<String>(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID //bind the data
    ).toTypedArray()

    private val CHANNEL_ID = "channel_id"
    public var notificationId = 101
    private val time = 0 //in milliseconds
    private val mNotificationTime = Calendar.getInstance().timeInMillis + time

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }


        createNotificationChannel()

        button2.setOnClickListener {
            sendNotification()
        }
//            findViewById<ListView>(R.id.listView).setOnClickListener { parent, view, position, id ->
//                // Get the selected item text from ListView
//                val selectedItem = parent.getItemAtPosition(position) as String
//                Log.i("tag", "gh")
//                Toast.makeText(this, "The best player is $selectedItem", Toast.LENGTH_LONG).show()//
//
//            }
//            val listView : ListView = findViewById(R.id.listView)

//            listView.setOnItemClickListener { parent, view, position, id ->
//                // Get the selected item text from ListView
//                val selectedItem = parent.getItemAtPosition(position) as String
//                Log.i("tag", "gh")
//                Toast.makeText(this, "The best player is $selectedItem", Toast.LENGTH_LONG).show()//
//
//            }

//            listView.setOnItemClickListener { parent, _, position, _ ->
//                val selectedItem = parent.getItemAtPosition(position) as String
////                Toast.makeText(this, "The best player is $selectedItem", Toast.LENGTH_LONG).show()//
//                    Log.i("tsg","msg")
//            }



        //ask for permission multiple times
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, Array(1) { Manifest.permission.READ_CONTACTS }, 111)
        } else {
            readContact()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            readContact()
        }
    }

    private fun readContact(){
        var from = listOf<String>( ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER).toTypedArray()

//            Log.i("tag", "ahh")

        var to = intArrayOf(android.R.id.text1, android.R.id.text2)

        //sort based on name
        var rs = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            cols, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)


//val adapter = ArrayAdapter(this, R.layout.book_list_item, R.id.book_title, array)
        var adapter = SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, rs, from, to, 0)
//            val listView : ListView = findViewById(R.id.listView)
        listView.adapter = adapter


//            listView.setOnItemClickListener { parent, view, position, id ->
//                val element = adapter.getItemAtPosition(position) // The item that was clicked
//            }
        listView.setOnItemClickListener { parent, view, position, id ->
            val element = adapter.getItem(position)// The item that was clicked
//                val intent = Intent(this, BookDetailActivity::class.java)
//                startActivity(intent)
            Log.i("tag", "onclickcontactyay")

            Toast.makeText(this, "The best player is $element", Toast.LENGTH_SHORT).show()//

                val intent = Intent(this, Edit::class.java)
                startActivity(intent)
        }


        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                var rs = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    cols,
                    "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ?",
                    Array(1){"%$p0%"},
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                adapter.changeCursor(rs)
                return false
            }

        })
    }


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

        val landingIntent = Intent(this, Receiver::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, landingIntent, 0)

        val callnowIntent = Intent(this, Receiver::class.java).apply {
            identifier = "now"
//            action = Context.ALARM_SERVICE
        }
//        callnowIntent.identifier = "now"
        val callnowPendingIntent: PendingIntent = PendingIntent.getBroadcast(this, 0, callnowIntent, 0)

        val snoozeIntent = Intent(this, Receiver::class.java).apply {
            identifier = "later"
        }
        val snoozePendingIntent: PendingIntent = PendingIntent.getBroadcast(this, 0, snoozeIntent, 0)

        val tomorrowIntent = Intent(this, Receiver::class.java).apply {
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


        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, notificationBuilder.build())
        }

    }




}