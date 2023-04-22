package com.example.doctorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class row_list : AppCompatActivity() {
    private lateinit var name: TextView
    private lateinit var date: TextView
    private lateinit var phone: TextView
    private lateinit var desc: TextView
    private lateinit var gender: TextView

    private var db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_row_list)

        name = findViewById(R.id.textView4)
        date = findViewById(R.id.textView5)
        phone = findViewById(R.id.textView6)
        desc = findViewById(R.id.textView7)
        gender = findViewById(R.id.textView8)

        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        val ref = db.collection("user").document(userId)
        ref.get().addOnSuccessListener {
            if (it!= null){
                val name1 = it.data?.get("iname")?.toString()
                val date1 = it.data?.get("idate")?.toString()
                val phone1 = it.data?.get("inumber")?.toString()
                val desc1 = it.data?.get("idesc")?.toString()
                val gender1 = it.data?.get("igender")?.toString()

                name.text = "Name: "+name1
                date.text = "Date: "+date1
                phone.text = "Phone: "+phone1
                desc.text = "Description: \n"+desc1
                gender.text = "Sex: "+gender1


            }
        }.addOnFailureListener{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }



    }
}