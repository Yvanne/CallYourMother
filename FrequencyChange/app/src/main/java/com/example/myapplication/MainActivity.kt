package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*


class MainActivity : AppCompatActivity() {
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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.contactName)
        numTimes = findViewById(R.id.editTextNumber)
        saveButton = findViewById(R.id.saveFreq)
        dayRadio = findViewById(R.id.dayFreq)
        weekRadio = findViewById(R.id.weekFreq)
        monthRadio = findViewById(R.id.monthFreq)
        yearRadio = findViewById(R.id.yearFreq)
        callChecked = findViewById(R.id.CallcheckBox)
        textChecked = findViewById(R.id.textCheckBox)

        // TODO: get the contact's name from mainActivity
        // probably through an intent


        // Are we editing calls, texts or boths
        if(callChecked.isChecked && textChecked.isChecked)
            reminderType = "both"
        else if(callChecked.isChecked)
            reminderType = "call"
        else if(textChecked.isChecked)
            reminderType = "text"


        // Saving user configurations
        saveButton.setOnClickListener {

            // TODO - gather ToDoItem data
            Toast.makeText(applicationContext, reminderType+" reminders set "+ numTimes.text.toString()+" times "+str, Toast.LENGTH_LONG).show()


            // TODO - return data Intent to main activity where this will also be sent to notification data

         /*   var dataIntent: Intent = Intent(this, SecondActivity.class)
            dataIntent.putExtra("reminder type", reminderType)
            dataIntent.putExtra("number of times", numTimes.toString())
            dataIntent.putExtra("frequency type", str)
            startActivity(dataIntent) */

        }


    }


    fun onCheckBoxClicked(view: View) {
        if(callChecked.isChecked && textChecked.isChecked)
            reminderType = "both"
        else if(callChecked.isChecked)
            reminderType = "call"
        else if(textChecked.isChecked)
            reminderType = "text"
        //Toast.makeText(applicationContext, reminderType+" reminder types selected", Toast.LENGTH_SHORT).show()
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

        Toast.makeText(applicationContext, str+" reminder selected", Toast.LENGTH_SHORT).show()
    }
}