package com.example.callyourmother

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : AppCompatActivity() {

    private var editEmail: EditText? = null
    private var resetBtn: Button? = null

    private var mAuth: FirebaseAuth? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        editEmail = findViewById(R.id.resetEmail)
        resetBtn = findViewById(R.id.resetButton)

        progressBar = findViewById(R.id.progressBar3)

        mAuth = FirebaseAuth.getInstance()

        resetBtn!!.setOnClickListener { resetPassword() }
    }

    private fun resetPassword() {
        var email: String = editEmail!!.text.toString().trim()

        if (email.isEmpty()) {
            editEmail!!.setError("Email required!")
            editEmail!!.requestFocus()
            return
        }

        // VALIDATE EMAIL
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail!!.setError(("Please enter a valid email address"))
            editEmail!!.requestFocus()
            return
        }

        progressBar!!.visibility = View.VISIBLE

        mAuth!!.sendPasswordResetEmail(email)
            .addOnCompleteListener(OnCompleteListener<Void>() { task ->
            if (task.isSuccessful) {
                progressBar!!.visibility = View.GONE
                Toast.makeText(applicationContext, "Check your email to reset your password", Toast.LENGTH_LONG).show()
            }
            else{
                progressBar!!.visibility = View.GONE
                Toast.makeText(applicationContext, "uh-oh something went wrong. Try again", Toast.LENGTH_LONG).show()
            }

            })

    }


}