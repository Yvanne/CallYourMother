package com.example.callyourmother

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class Login : AppCompatActivity(), View.OnClickListener {
    private lateinit var register:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        register = findViewById(R.id.register)
        register.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id) {
                // when you click on register, you're taken to the Registration activity
                R.id.register -> startActivity(Intent(this, Registration::class.java))
            }
        }
    }
}