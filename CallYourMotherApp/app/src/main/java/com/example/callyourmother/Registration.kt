package com.example.callyourmother

import android.content.Intent
import android.os.Bundle
import android.util.Patterns.*
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class Registration : AppCompatActivity(), View.OnClickListener {

    private var mAuth: FirebaseAuth? = null
    private var registrationBanner: TextView? = null
    private var userRegistration: Button? = null
    private var username: EditText? = null
    private var email: EditText? = null
    private var password: EditText? = null
    private var progressBar: ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        mAuth = FirebaseAuth.getInstance()

        registrationBanner = findViewById(R.id.bannerReg)
        registrationBanner!!.setOnClickListener(this)

        userRegistration = findViewById(R.id.regButton)
        userRegistration!!.setOnClickListener(this)

        username = findViewById(R.id.editTextTextPersonName)
        email = findViewById(R.id.editTextTextEmailAddress2)
        password = findViewById(R.id.editTextTextPassword2)

        progressBar = findViewById(R.id.progressBar2)

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id) {
                // when you click on title , you're taken back to the login activity
                R.id.bannerReg -> startActivity(Intent(this, Login::class.java))
                R.id.regButton -> registerUser()
            }
        }

    }

    private fun registerUser() {
        var textEmail:String = email!!.text.toString().trim()
        var textName:String = username!!.text.toString().trim()
        var textPassword:String = password!!.text.toString().trim()

        if (textName.isEmpty()) {
            username!!.setError("Name required!")
            username!!.requestFocus()
            return
        }

        if (textEmail.isEmpty()) {
            email!!.setError("Email required!")
            email!!.requestFocus()
            return
        }
        // VALIDATE EMAIL
        if(!EMAIL_ADDRESS.matcher(textEmail).matches()) {
            email!!.setError(("Please enter a valid email address"))
            email!!.requestFocus()
            return
        }

        if (textPassword.isEmpty()) {
            password!!.setError("Password required!")
            password!!.requestFocus()
            return
        }
        if(textPassword.length < 6) {
            password!!.setError("Your password should be 6 or more letters long")
            password!!.requestFocus()
            return
        }

        progressBar!!.visibility = View.VISIBLE
        mAuth!!.createUserWithEmailAndPassword(textEmail, textPassword)
            .addOnCompleteListener(OnCompleteListener<AuthResult>() { task ->
                if (task.isSuccessful) {
                    var user: User = User(textName,textEmail)
                    FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .setValue(user).addOnCompleteListener( OnCompleteListener<Void> { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(applicationContext, "New user registration successful!", Toast.LENGTH_LONG).show()
                                progressBar!!.visibility = View.GONE

                            } else {
                                //display a failure message
                                Toast.makeText(applicationContext, "user registration unsuccessful!", Toast.LENGTH_LONG).show()
                                progressBar!!.visibility = View.GONE
                            }
                        })
                } else {
                    Toast.makeText(applicationContext, "Registration failed! Please try again later", Toast.LENGTH_LONG).show()
                    progressBar!!.visibility = View.GONE
                }
            })

    }
}