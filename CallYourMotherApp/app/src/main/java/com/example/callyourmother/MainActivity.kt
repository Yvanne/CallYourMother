package com.example.callyourmother

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView

import android.widget.SimpleCursorAdapter
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
            setSupportActionBar(findViewById(R.id.toolbar))

            findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }

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



            var adapter = SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, rs, from, to, 0)

            listView.adapter = adapter

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

//            override fun onQueryTextSubmit(query: String?): Boolean {
//                Log.i(TAG,"Llego al querysubmit")
//                return false
//            }
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


}