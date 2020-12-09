package com.example.callyourmother

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class UserInfo : AppCompatActivity() {
    private var user: FirebaseUser? = null
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var userID: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        setSupportActionBar(findViewById(R.id.toolbar))


        user = FirebaseAuth.getInstance().currentUser
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

        userID = user!!.uid

        val name: TextView = findViewById(R.id.accName)
        val email: TextView = findViewById(R.id.accEmail)
        // displays user information saved in firebase
        mDatabaseReference!!.child(userID!!)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var userProfile: User? = dataSnapshot.getValue(User::class.java)
                    if (userProfile != null) {
                        var fullName = userProfile.textName
                        var fullEmail = userProfile.textEmail

                        name!!.setText(fullName)
                        email!!.setText(fullEmail)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(
                        applicationContext,
                        "Ooops! Something went wrong",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

    }
}