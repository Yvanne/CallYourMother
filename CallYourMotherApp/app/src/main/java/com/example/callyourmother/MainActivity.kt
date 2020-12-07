package com.example.callyourmother

import android.Manifest
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

//yes
class MainActivity : AppCompatActivity() {
    //test!!!!
    var cols = listOf<String>(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID //bind the data
    ).toTypedArray()

    private val CHANNEL_ID = "channel_id"
    public var notificationId = 101
    public var contactName: String? = null
    public var contactNum: String? = null
    val delay: Long = 1000 //in milliseconds
     var i:Intent? = null
     var  type: String? = null
    var times : String? = null
    var freq : String? = null
//    private val mNotificationTime = Calendar.getInstance().timeInMillis + time


//    var yourBR: Receiver? = null
//    yourBR = Receiver()
//    yourBR.setMainActivityHandler(this)
//    val callInterceptorIntentFilter = IntentFilter("android.intent.action.ANY_ACTION")
//    registerReceiver(yourBR, callInterceptorIntentFilter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

//        val delay = 3000
//        alarmHandle(delay)
        i = intent
        registerReceiver(broadcastReceiver, IntentFilter("broadCastName"));
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }


        createNotificationChannel()

        button2.setOnClickListener {
            sendNotification(0)
//            NotificationUtils().setNotification(delay.toLong(), this@MainActivity)
        }
        button.setOnClickListener {
            val intent = Intent(this, Edit::class.java)
            startActivity(intent)
          //  finish()

             i = getIntent()
             type = i!!.getStringExtra("reminder type")//text/call
             times = i!!.getStringExtra("number of times")
             freq = i!!.getStringExtra("frequency type")//day/week/month/year
            contactName = i!!.getStringExtra("name")
            contactNum = i!!.getStringExtra("phone")
            Log.i("TAG", type + " HERE "+ contactNum + " "+i)

        }

        //ask for permission multiple times
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                Array(1) { Manifest.permission.READ_CONTACTS },
                111
            )
        } else {
            readContact()
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                Array(1) { Manifest.permission.CALL_PHONE },
                112
            )///
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            readContact()
        }
        /*     if(requestCode == 112 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                 if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                     ActivityCompat.requestPermissions(this, Array(1) { Manifest.permission.CALL_PHONE }, 112)///
                     Toast.makeText(applicationContext, "This permission is required for this app.", Toast.LENGTH_LONG).show()
                 }
             }*/

    }


    /*fun phonecall() {
        val intent = Intent(Intent.ACTION_CALL);
        intent.data = Uri.parse("tel:"+ contactNum)
        startActivity(intent)
    }*/

    private fun readContact() {
        var from = listOf<String>(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        ).toTypedArray()

//            Log.i("tag", "ahh")

        var to = intArrayOf(android.R.id.text1, android.R.id.text2)

        //sort based on name
        var rs = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            cols, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )
        //contactName = rs?.getString(0)
        //contactNum = rs?.getString(1)

//MASTER CONTACT LIST
        var adapter =
            SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, rs, from, to, 0)
//            val listView : ListView = findViewById(R.id.listView)
        listView.adapter = adapter
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent: AdapterView<*>, view: View?, position, id -> // Item position is in the variable position.
                Toast.makeText(
                    applicationContext,
                    "Editing reminder frequency for: " + rs?.getString(0),
                    Toast.LENGTH_LONG
                ).show()
                var dataMain: Intent = Intent(this, Edit::class.java)
                dataMain.putExtra("name", rs?.getString(0))
                dataMain.putExtra("phone",rs?.getString(1) )
                startActivity(dataMain)
                //finish()


                /*i = getIntent()
                type = i!!.getStringExtra("reminder type")//text/call
                times = i!!.getStringExtra("number of times")
                freq = i!!.getStringExtra("frequency type")//day/week/month/year
                contactName = i!!.getStringExtra("name")
                contactNum = i!!.getStringExtra("phone")*/

               // val intent:Intent = getIntent()
                Log.i("TAG", "IS NULLER"+i?.extras)

                val bundle :Bundle ?= i?.extras
                if (bundle!=null){
                    //     val message = bundle.getString("object") // 1
                    Log.i("TAG", type + " HeeeeeeeERE "+ contactNum )

                    //Toast.makeText(applicationContext, "pls work pls pls", Toast.LENGTH_SHORT).show()
                    determineDelay()
                } else {
                    Log.i("TAG", "IS NULL")

                    Toast.makeText(applicationContext, "CAN'T send", Toast.LENGTH_SHORT).show()
                }


                // onActivityResult(8, RESULT_OK, )
                //dataIntent.getStringExtra("name")?.let { Log.i("TAG", it) }
            }



            // SEARCHING FOR CONTACTS
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                var rs = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    cols,
                    "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ?",
                    Array(1) { "%$p0%" },
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                )
                adapter.changeCursor(rs)
                listView.onItemClickListener =
                    AdapterView.OnItemClickListener { parent: AdapterView<*>, view: View?, position, id -> // Item position is in the variable position.
                        Toast.makeText(
                            applicationContext,
                            "Editing reminder frequency for: " + rs?.getString(0),
                            Toast.LENGTH_LONG
                        ).show()

                        var dataIntent: Intent = Intent(this@MainActivity, Edit::class.java)
                        dataIntent.putExtra("name", rs?.getString(0))
                        dataIntent.putExtra("phone",rs?.getString(1) )
                        startActivity(dataIntent)
                        finish()

                        val bundle :Bundle ?=dataIntent.extras
                       if (bundle!=null){
                            //     val message = bundle.getString("object") // 1
                            Toast.makeText(applicationContext, "pls work pls pls", Toast.LENGTH_SHORT).show()
                            determineDelay()
                        } else {
                            Toast.makeText(applicationContext, "CAN'T send", Toast.LENGTH_SHORT).show()
                        }
                    }
                return false
            }

        })
    }

    fun phonecall() {
        val intent = Intent(Intent.ACTION_CALL);
        intent.data = Uri.parse("tel:$contactNum")
        startActivity(intent)
    }

    fun determineDelay() {
        Log.i("TAG", "HELOOOOOOOOOOOOO")
        val day : Long = 86400000
        val week : Long = day * 7
        val month : Long = day * 30
        val year : Long = week * 52

        /*val i: Intent = intent //getIntent()
        val type = i.getStringExtra("reminder type")//text/call
        val times = i.getStringExtra("number of times")
        val freq = i.getStringExtra("frequency type")//day/week/month/year
        contactName = i.getStringExtra("name")*/

       // val time = times?.toLong()
        var  ret :Long  = 3000

    /*    if (time != null) {
            Log.i("TAG", time.toString())
        }
        if (type != null) {
            Log.i("TAG", type)
        }
        if (freq != null) {
            Log.i("TAG", freq)
        }*/
        if(freq === "day"){
            if (times != null) {
                ret =  day/ times!!.toLong()
            }
        } else if (freq == "week"){
            if (times != null) {
                ret = week/ times!!.toLong()
            }
        } else if (freq == "month"){
            if (times != null) {
                ret = month/ times!!.toLong()
            }
        } else {//year
            if (times != null) {
                ret =  year/ times!!.toLong()
            }
        }
//        return freq/times

        sendNotification(ret)

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
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(applicationContext, "Logged out", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@MainActivity, Login::class.java))
                return true
            }
            R.id.account -> {
                startActivity(Intent(this@MainActivity, UserInfo::class.java))
                return true
            }

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
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    fun sendNotification(del: Long) {


        val landingIntent = Intent(this, Receiver::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, landingIntent, 0)


        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent)


        val callnowIntent = Intent(this, Receiver::class.java).apply {
            identifier = "now"
//            action = Intent.ACTION_CALL
        }
//        callnowIntent.identifier = "now"
        val callnowPendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this, 0, callnowIntent, 0)

        val snoozeIntent = Intent(this, Receiver::class.java).apply {
            identifier = "later"
        }
        val snoozePendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this, 0, snoozeIntent, 0)

        val tomorrowIntent = Intent(this, Receiver::class.java).apply {
            identifier = "tomorrow"
        }
        val tomorrowPendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this, 0, tomorrowIntent, 0)

        Handler().postDelayed({
            val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setContentTitle("Call " + contactName)
                .setContentText("help me :(")
                .setColor(ContextCompat.getColor(this, R.color.notificationBlue))
//            .setAutoCancel(true)
//            .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_launcher_foreground, "Call Now", callnowPendingIntent)
                .addAction(
                    R.drawable.ic_launcher_foreground,
                    "Snooze for 2 hours",
                    snoozePendingIntent
                )
                .addAction(
                    R.drawable.ic_launcher_foreground,
                    "Call Tomorrow",
                    tomorrowPendingIntent
                )
                .setAutoCancel(true)


//        Timer().schedule(2000){
//
//        }

            with(NotificationManagerCompat.from(this)) {
                notify(notificationId, notificationBuilder.build())
            }


//    Handler().postDelayed({
//        //doSomethingHere()
        }, del)


    }


    var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val b = intent.extras
            val message = b!!.getString("message")
            Log.i("tag", "" + message)


//            val id = intent.extras
            val id = intent.extras!!.getString("identifier")

            val length = Toast.LENGTH_SHORT
            if (id == "now") {
                Log.i("tag", "clicked call now");
                Toast.makeText(context, "calling", length).show()//

                phonecall()

//                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//                notificationManager.cancel(MainActivity().notificationId)
            }
            if (id == "later") {
                Log.i("tag", "clicked snooze");
                Toast.makeText(context, "Reminder Snoozed", length).show()//

//                time = (System.currentTimeMillis() + 7200000)
                val d: Long = 2000
                Log.i("tag", d.toString())

                sendNotification(7200000)
            }


            if (id == "tomorrow") {
                Log.i("tag", "clicked tomorrow");
                Toast.makeText(context, "Snoozed for 24 hours.", length).show()//

//                time = (System.currentTimeMillis() + 86400000)
                sendNotification(86400000)

            }

        }
    }


}