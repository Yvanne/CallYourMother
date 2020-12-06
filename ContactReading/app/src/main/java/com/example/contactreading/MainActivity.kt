package com.example.contactreading

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var cols = listOf<String>(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID //bind the data
    ).toTypedArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ask for permission multiple times
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                Array(1) { Manifest.permission.READ_CONTACTS },
                111
            )
        } else {
            readContact()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            readContact()
        }
    }
    private fun readContact(){
        var from = listOf<String>(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        ).toTypedArray()

        var to = intArrayOf(android.R.id.text1, android.R.id.text2)

        //sort based on name
        var rs = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            cols, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )

       // if(rs?.moveToNext()!!)
         //   Toast.makeText(applicationContext, rs.getString(1), Toast.LENGTH_LONG).show()

        var adapter = SimpleCursorAdapter(
            this,
            android.R.layout.simple_list_item_2,
            rs,
            from,
            to,
            0
        )

       listView.adapter = adapter

        listView.onItemClickListener =
            OnItemClickListener { parent: AdapterView<*>, view: View?, position, id -> // Item position is in the variable position.
                var n = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                // Display item position in toast.
                Toast.makeText(applicationContext, "The position is: $position $n", Toast.LENGTH_SHORT).show()

            }




        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                var rs = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    cols, "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ?",
                    Array(1) { "%$p0%" }, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                )
                adapter.changeCursor(rs)
                return false
            }
        })


       /* listView.setOnItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // Get the selected item text from ListView
            val selectedItem = parent.getItemAtPosition(position) as String

            /* val intent = //Intent(this, EditFrequency::class.java)*/
            startActivity(intent)
        }
        val cursor =
            contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        while (cursor!!.moveToNext()) {
            val contactId =
                cursor!!.getString(cursor!!.getColumnIndex(ContactsContract.Contacts._ID))
            val hasPhone =
                cursor!!.getString(cursor!!.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
            val name =
                cursor!!.getString(cursor!!.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
            if ("1" == hasPhone || java.lang.Boolean.parseBoolean(hasPhone)) {
                // has a number so now query it like this
                val phones: Cursor? = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null,
                    null
                )
                while (phones?.moveToNext() == true) {
                    val phoneNumber =
                        phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    val type =
                        phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE))
                    val isMobile = type == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE ||
                            type == ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE

                   // Toast.makeText(applicationContext, "toast", Toast.LENGTH_SHORT).show()
                    // Do something here with 'phoneNumber' such as saving into
                    // the List or Array that will be used
                    phones!!.close()

                }
            }
        }
        */


    }
}

