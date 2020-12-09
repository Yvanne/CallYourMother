package com.example.callyourmother

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Login : AppCompatActivity(), View.OnClickListener {
    private lateinit var register: TextView
    private var forgotPass: TextView? = null
    private var userEmail: EditText? = null
    private var userPassword: EditText? = null
    private var signIn: Button? = null

    private var mAuth: FirebaseAuth? = null
    private var progressBar: ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()

        register = findViewById(R.id.register)
        register.setOnClickListener(this)

        signIn = findViewById(R.id.loginButton)
        signIn!!.setOnClickListener(this)
        userEmail = findViewById(R.id.editTextTextEmailAddress)
        userPassword = findViewById(R.id.editTextTextPassword)
        forgotPass = findViewById(R.id.forgotPassword)
        forgotPass!!.setOnClickListener(this)


        progressBar = findViewById(R.id.progressBar)

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                // when you click on register, you're taken to the Registration activity
                R.id.register -> startActivity(Intent(this, Registration::class.java))
                R.id.loginButton -> userLogin()
                R.id.forgotPassword -> startActivity(Intent(this, ForgotPassword::class.java))
            }
        }
    }

    private fun userLogin() {
        var email: String = userEmail!!.text.toString().trim()
        var password: String = userPassword!!.text.toString().trim()



        if (email.isEmpty()) {
            userEmail!!.setError("Email required!")
            userEmail!!.requestFocus()
            return
        }

        // VALIDATE EMAIL
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userEmail!!.setError(("Please enter a valid email address"))
            userEmail!!.requestFocus()
            return
        }

        if (password.isEmpty()) {
            userPassword!!.setError("Password required!")
            userPassword!!.requestFocus()
            return
        }
        //VALIDATE PASSWORD
        if (password.length < 6) {
            userPassword!!.setError("Your password should be 6 or more letters long")
            userPassword!!.requestFocus()
            return
        }

        progressBar!!.visibility = View.VISIBLE
        // check if credentials are found in the database
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressBar!!.visibility = View.GONE
                if (task.isSuccessful) {
                    var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
                    if (user!!.isEmailVerified) {
                        Toast.makeText(applicationContext, "Login successful!", Toast.LENGTH_LONG)
                            .show()
                        val intent = Intent(this@Login, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        user.sendEmailVerification()
                        Toast.makeText(
                            applicationContext,
                            "check your email to verify your account",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                }
                // If sign in fails, display a message to the user.
                else {
                    Toast.makeText(
                        applicationContext,
                        "Login failed! Please try again later",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }


    }
}
