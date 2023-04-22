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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class Form : AppCompatActivity() {
    private lateinit var PersonName: EditText
    private lateinit var DOB: EditText
    private lateinit var Contact: EditText
    private lateinit var reason: EditText
    private lateinit var gender: RadioGroup

    private var db = Firebase.firestore

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

        val selectedID: Int = gender.checkedRadioButtonId
        val radioButton = findViewById<RadioButton>(selectedID)

        button.setOnClickListener {
            val name = PersonName.text.toString().trim()
            val date = DOB.text.toString().trim()
            val number = Contact.text.toString().trim()
            val desc = reason.text.toString().trim()
            val gender1 = radioButton!!.text.toString().trim()


            val userMap = hashMapOf(
                "iname" to name,
                "idate" to date,
                "inumber" to number,
                "idesc" to desc,
                "igender" to gender1
            )
            val userId = FirebaseAuth.getInstance().currentUser!!.uid
            db.collection("user").document(userId).set(userMap).addOnSuccessListener {
                Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show()
                PersonName.text.clear()
                DOB.text.clear()
                Contact.text.clear()
                reason.text.clear()

                val intent = Intent(this, row_list::class.java)
                startActivity(intent)
                finish()

            }.addOnFailureListener {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
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
        dpd.datePicker.minDate = System.currentTimeMillis() - 1000
        dpd.show()
    }

}