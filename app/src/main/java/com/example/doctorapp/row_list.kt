package com.example.doctorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView

class row_list : AppCompatActivity() {
    private lateinit var textview4: TextView
    private lateinit var textview5: TextView
    private lateinit var textview6: TextView
    private lateinit var textview7: TextView
    private lateinit var textview8: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_row_list)

        textview4 = findViewById(R.id.textView4)
        textview5 = findViewById(R.id.textView5)
        textview6 = findViewById(R.id.textView6)
        textview7 = findViewById(R.id.textView7)
        textview8 = findViewById(R.id.textView8)

        val personname = intent.getStringExtra("PersonName")
        val dob = intent.getStringExtra("DOB")
        val contact = intent.getStringExtra("Contact")
        val reason = intent.getStringExtra("reason")
        val gender = intent.getStringExtra("gender")

        textview4.text = "Name: "+personname
        textview5.text = "Date: "+dob
        textview6.text = "Phone: "+contact
        textview7.text = "Desc: "+reason
        textview8.text = "Gender: "+gender

    }
}