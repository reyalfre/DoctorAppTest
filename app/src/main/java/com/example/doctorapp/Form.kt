package com.example.doctorapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import java.util.*

class Form : AppCompatActivity() {
    private lateinit var PersonName: EditText
    private lateinit var DOB: EditText
    private lateinit var Contact: EditText
    private lateinit var reason: EditText
    private lateinit var gender: RadioGroup




    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        PersonName = findViewById(R.id.PersonName)
        DOB = findViewById(R.id.DOB)
        Contact = findViewById(R.id.Contact)
        reason = findViewById(R.id.reason)
        gender = findViewById(R.id.radioGroup)
        val button = findViewById<Button>(R.id.button)
        val backbtn = findViewById<ImageButton>(R.id.backbtn)
        val selectedID = gender.checkedRadioButtonId
        val radioButton = findViewById<RadioButton>(selectedID)
        button.setOnClickListener{
            startActivity( Intent(this, row_list::class.java)
                .putExtra("PersonName", PersonName.text.toString())
                .putExtra("DOB", DOB.text.toString())
                .putExtra("Contact", Contact.text.toString())
                .putExtra("reason", reason.text.toString())
                .putExtra("gender", radioButton.text.toString()))

            Toast.makeText(this, "Form Submitted", Toast.LENGTH_SHORT).show()
        }
        backbtn.setOnClickListener{
            startActivity( Intent(this, DashboardActivity::class.java))
        }
        val datepicker = findViewById<ImageButton>(R.id.datepicker)

        datepicker.setOnClickListener {
            pickdate()
        }
    }
    private fun pickdate(){
        val dob = findViewById<EditText>(R.id.DOB)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // Display Selected date in textbox
            dob.setText("" + dayOfMonth + "/" + month + "/" + year)
        }, year, month, day)
        dpd.show()
    }

}