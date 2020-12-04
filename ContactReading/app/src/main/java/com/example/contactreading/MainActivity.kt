package com.example.contactreading

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.AdapterView
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
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

        var to = intArrayOf(android.R.id.text1, android.R.id.text2)

        //sort based on name
        var rs = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        cols, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)

       // if(rs?.moveToNext()!!)
         //   Toast.makeText(applicationContext, rs.getString(1), Toast.LENGTH_LONG).show()

        var adapter = SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, rs, from, to, 0)

        listView.adapter = adapter

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                var rs = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                cols, "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ?",
                    Array(1){"%$p0%"}, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                adapter.changeCursor(rs)
                return false
            }
        })


       /* listView.setOnItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // Get the selected item text from ListView
            val selectedItem = parent.getItemAtPosition(position) as String

            /* val intent = //Intent(this, EditFrequency::class.java)*/
            startActivity(intent)
        }*/

    }
}

