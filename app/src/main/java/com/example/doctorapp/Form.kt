package com.example.doctorapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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

    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101

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
        createNotificationChannel()

        button.setOnClickListener {

            val name = PersonName.text.toString().trim()
            val date = DOB.text.toString().trim()
            val number = Contact.text.toString().trim()
            val desc = reason.text.toString().trim()
            val gender1 = radioButton.text.toString().trim()


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
                sendNotification()
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

    private fun createNotificationChannel(){
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = android.app.NotificationManager.IMPORTANCE_DEFAULT
            val channel = android.app.NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText


            }
            // Register the channel with the system
            val notificationManager: android.app.NotificationManager = getSystemService(android.app.NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }


    private fun sendNotification(){
        val intent = Intent(this, row_list::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        } else {
            TODO("VERSION.SDK_INT < S")
        }


        val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.doctor)
        val bitmapLargeIcon = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.docsign)


        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Appointment Booked")
            .setContentText("Tap to view your entered details")
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setLargeIcon(bitmapLargeIcon)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        with(NotificationManagerCompat.from(this)){
            notify(notificationId, builder.build())
        }

    }



}