package com.example.callapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NoActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_second)

//        Toast.makeText(Context, "ASDFGHJKL", Toast.LENGTH_SHORT).show()
        Log.i("tag","nope");
    }

}