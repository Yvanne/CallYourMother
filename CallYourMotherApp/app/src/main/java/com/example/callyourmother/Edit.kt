package com.example.callyourmother

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Edit : AppCompatActivity() {
    private lateinit var name: TextView
    private lateinit var numTimes: EditText
    private  var reminderType: String? = null
    private var str:String? = null
    private lateinit var saveButton: Button
    private lateinit var selectedRadioButton: RadioGroup
    private lateinit var dayRadio: RadioButton
    private lateinit var weekRadio: RadioButton
    private lateinit var monthRadio: RadioButton
    private lateinit var yearRadio: RadioButton
    private lateinit var textChecked: CheckBox
    private lateinit var callChecked: CheckBox
    private lateinit var ref: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)
        setSupportActionBar(findViewById(R.id.toolbar))


        name = findViewById(R.id.contactName)
        numTimes = findViewById(R.id.editTextNumber)
        saveButton = findViewById(R.id.saveFreq)
        dayRadio = findViewById(R.id.dayFreq)
        weekRadio = findViewById(R.id.weekFreq)
        monthRadio = findViewById(R.id.monthFreq)
        yearRadio = findViewById(R.id.yearFreq)
        callChecked = findViewById(R.id.CallcheckBox)
        textChecked = findViewById(R.id.textCheckBox)
        var contact: Contact = Contact()
        ref = FirebaseDatabase.getInstance().reference.child("Contacts");



        // TODO: get the contact's name from mainActivity
        // probably through an intent
        val i: Intent = intent //getIntent()
        val type= i.getStringExtra("name")
        val phone = i.getStringExtra("phone")
        name.setText(type)



        // Are we editing calls, texts or boths
        if(callChecked.isChecked && textChecked.isChecked)
            reminderType = "both"
        else if(callChecked.isChecked)
            reminderType = "call"
        else if(textChecked.isChecked)
            reminderType = "text"


        // Saving user configurations
        saveButton.setOnClickListener {

            //Error if user enters wrong settings
          //  if (reminderType.isNullOrEmpty() ||  numTimes.text.toString() == "" || numTimes.text.toString() == "0" || str.isNullOrEmpty()) {
               // Toast.makeText(applicationContext, "Please enter correct settings", Toast.LENGTH_LONG).show()
           // }
            //else {
                // TODO - gather ToDoItem data
                Toast.makeText(applicationContext, reminderType + " reminders set " + numTimes.text.toString() + " times " + str + " for "+ type, Toast.LENGTH_LONG).show()

                // TODO - return data Intent to main activity where this will also be sent to notification data
                var dataIntent: Intent = Intent(this, MainActivity::class.java)
                dataIntent.putExtra("reminder type", reminderType)
                dataIntent.putExtra("number of times", numTimes.toString())
                dataIntent.putExtra("frequency type", str)
                dataIntent.putExtra("name", name.toString())
                dataIntent.putExtra("phone", phone)
                startActivity(dataIntent)
                //MainActivity().determineDelay()



            // SAVING USER SETTING INFO TO FIREBASE
                contact.name = type
                contact.setting = reminderType + " reminders set " + numTimes.text.toString() + " times " + str
                ref.push().setValue(contact)
                Toast.makeText(applicationContext, "saved", Toast.LENGTH_LONG).show()

            }
       // }


    }


    fun onCheckBoxClicked(view: View) {
        if(callChecked.isChecked && textChecked.isChecked)
            reminderType = "both"
        else if(callChecked.isChecked)
            reminderType = "call"
        else if(textChecked.isChecked)
            reminderType = "text"
    }

    // Get how often to be reminded such as by days, weeks, months or years
    fun onRadioButtonClicked(view: View) {
        if (dayRadio.isChecked)
            str = "Daily"
        else if(weekRadio.isChecked)
            str = "Weekly"
        else if (monthRadio.isChecked)
            str = "Monthly"
        else
            str = "Yearly"

    }
}