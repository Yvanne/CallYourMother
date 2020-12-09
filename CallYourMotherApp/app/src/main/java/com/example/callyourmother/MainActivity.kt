package com.example.callyourmother

import android.Manifest
import android.app.*
import android.content.*
import android.content.pm.PackageManager
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //organize the retreival of contacts
    var cols = listOf<String>(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID //bind the data
    ).toTypedArray()

    private val CHANNEL_ID = "channel_id"
    var notificationId = 101
    var contactName: String? = null
    var contactNum: String? = null
    var i: Intent? = null
    var type: String? = null
    var times: String? = null
    var freq: String? = null

    // image buttons
    private var buttonContacts: ImageButton? = null
    private var editButton: ImageButton? = null
    private var instructButton: ImageButton? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        buttonContacts = findViewById(R.id.imageButton)
        editButton = findViewById(R.id.imageButton2)
        instructButton = findViewById(R.id.imageButton3)

        // displays user instructions for the app
        instructButton?.setOnClickListener {
            // build alert dialog
            val dialogBuilder = AlertDialog.Builder(this)

            // set message of alert dialog
            dialogBuilder.setMessage(
                "Go to the All Contacts tab in the lower left corner. Then, select the contact you \n" +
                        "want to set reminders for. You will be prompted to edit the frequency of the reminder. \n" +
                        "Save your preferences. You are done setting the reminder! \n" +
                        "You can see the list of contacts you edited in the middle tab called Edited Contacts."
            )
                .setCancelable(true)

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("Welcome to CallYourMother! Here are the instructions: ")
            // show alert dialog
            alert.show()
        }

        // gets values passed by edit activity
        i = intent
        type = i!!.getStringExtra("reminder type")//text/call
        times = i!!.getStringExtra("number of times")
        freq = i!!.getStringExtra("frequency type")//day/week/month/year
        contactName = i!!.getStringExtra("name")
        contactNum = i!!.getStringExtra("phone");

        if (i != null) {
            /*only call this function if the intent has been received from Edit and all the fields
              are popuated and not null */
            determineDelay(type, times, freq)
        } else {
            //do nothing when null
            Log.i("TESTTAG", "data sent null")
        }
        registerReceiver(broadcastReceiver, IntentFilter("broadCastName"));
        createNotificationChannel()

        editButton?.setOnClickListener {
            sendNotification(5000)

        }

        //ask user for permission to access phone contacts multiple times
        //if permission is accepted then call the readContact() function
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
// ask the user permission to call the contact from the app
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                Array(1) { Manifest.permission.CALL_PHONE },
                112
            )
        }
    }
//check again to see if the user granted the correct permission for contact reading
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            readContact()
        }

    }

//private function to read through the user's contact name and number
    private fun readContact() {
    //retreive the list of names and numbers and store into the from array
        var from = listOf<String>(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        ).toTypedArray()

        //store the name and number from the phone
        var to = intArrayOf(android.R.id.text1, android.R.id.text2)

        //sort based on name
        var rs = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            cols, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )

    //connect the contacts and numbers with adapter and display in listview in alphabetical order
        var adapter =
            SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, rs, from, to, 0)
//            val listView : ListView = findViewById(R.id.listView)
        listView.adapter = adapter

    //make each contact clickable so that user can edit the contact for frequency
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent: AdapterView<*>, view: View?, position, id -> // Item position is in the variable position.
                Toast.makeText(
                    applicationContext,
                    "Editing reminder frequency for: " + rs?.getString(0),
                    Toast.LENGTH_LONG
                ).show()
                /*launch the Edit class and send the specific name and number of selected contact along*/
                var dataMain: Intent = Intent(this, Edit::class.java)
                dataMain.putExtra("name", rs?.getString(0))
                dataMain.putExtra("phone", rs?.getString(1))
                startActivity(dataMain)
                finish()

            }


        // SEARCHING FOR CONTACTS
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
            //search the list of contacts and match the string based on user input
            override fun onQueryTextChange(p0: String?): Boolean {
                var rs = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    cols,
                    "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ?",
                    Array(1) { "%$p0%" },
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                )
                adapter.changeCursor(rs)
                //make each contact clickable after search
                listView.onItemClickListener =
                    AdapterView.OnItemClickListener { parent: AdapterView<*>, view: View?, position, id -> // Item position is in the variable position.
                        Toast.makeText(
                            applicationContext,
                            "Editing reminder frequency for: " + rs?.getString(0),
                            Toast.LENGTH_LONG
                        ).show()
                        /*launch the Edit class and send the specific name and number of selected contact along*/
                        var dataIntent: Intent = Intent(this@MainActivity, Edit::class.java)
                        dataIntent.putExtra("name", rs?.getString(0))
                        dataIntent.putExtra("phone", rs?.getString(1))
                        startActivity(dataIntent)
                        finish()
                    }
                return false
            }

        })
    }

    /*place a phonecall to the selected contacct*/
    fun phonecall() {
        val intent = Intent(Intent.ACTION_CALL);
        intent.data = Uri.parse("tel:$contactNum")
        startActivity(intent)
    }

    // Calculates how often the notification will be sent
    fun determineDelay(type1: String?, times1: String?, freq1: String?) {
        Log.i("TAG", "entered determineDelay")
        val day: Long = 86400000
        val week: Long = day * 7
        val month: Long = day * 30
        val year: Long = week * 52


        Log.i("TAG", "IS exras: " + i?.extras)

        if (times1 != null) {
            Log.i("TAG", times.toString())
        }
        if (type1 != null) {
            Log.i("TAG", type.toString())
        }
        if (freq1 != null) {
            Log.i("TAG", "frequency: " + freq.toString())
        }
        if (freq1 == "daily") {
            if (times1 != null) {
                val ret = day / times1!!.toLong()
                sendNotification(ret)
            }
        } else if (freq1 == "weekly") {
            if (times1 != null) {
                val ret = week / times1!!.toLong()
                sendNotification(ret)
            }
        } else if (freq1 == "monthly") {
            if (times1 != null) {
                val ret = month / times1!!.toLong()
                sendNotification(ret)
            }
        } else {//year
            if (times1 != null) {
                val ret = year / times1!!.toLong()
                sendNotification(ret)
            }
        }

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

//function that builds and sends the notification
    fun sendNotification(del: Long) {

        //opens notification selection based off of the user selection
        val landingIntent = Intent(this, Receiver::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, landingIntent, 0)

        val callnowIntent = Intent(this, Receiver::class.java).apply {
            identifier = "now"
        }
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
                .setContentTitle("Hey! you should give $contactName a $type!")
                .setContentText("You set up a reminder to $type them $freq.")
                .setColor(ContextCompat.getColor(this, R.color.notificationBlue))

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


            with(NotificationManagerCompat.from(this)) {
                notify(notificationId, notificationBuilder.build())
            }


        }, del)


    }

    /*aligns with Receiver.kt to react to notification action buttons; is located in this class so as
    * to allow visibility of functions called*/
    var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val b = intent.extras
            val message = b!!.getString("message")
            Log.i("tag", "" + message)

            val id = intent.extras!!.getString("identifier")

            val length = Toast.LENGTH_SHORT
            if (id == "now") {
                Log.i("tag", "clicked call now");
                Toast.makeText(context, "calling", length).show()//

                phonecall()

            }
            if (id == "later") {
                Log.i("tag", "clicked snooze");
                Toast.makeText(context, "Reminder Snoozed", length).show()//

                val d: Long = 2000
                Log.i("tag", d.toString())

                sendNotification(7200000)
            }


            if (id == "tomorrow") {
                Log.i("tag", "clicked tomorrow");
                Toast.makeText(context, "Snoozed for 24 hours.", length).show()//

                sendNotification(86400000)

            }

        }
    }


}