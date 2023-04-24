package com.example.doctorapp

import android.app.Person
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class List : AppCompatActivity() {

    private lateinit var personRecyclerView: RecyclerView
    private lateinit var loading: TextView
    private lateinit var personArrayList: ArrayList<PersonModel>
    private var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        personRecyclerView = findViewById(R.id.recyclerView)
        personRecyclerView.layoutManager = LinearLayoutManager(this)
        loading = findViewById(R.id.loading)

        personArrayList = arrayListOf()

        loading.visibility = View.VISIBLE
        db = FirebaseFirestore.getInstance()
        db.collection("user").get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for(data in it.documents){
                        val user: PersonModel? = data.toObject(PersonModel::class.java)
                        if(user!=null){
                            personArrayList.add(user)
                        }
                    }
                    personRecyclerView.adapter = RecycleViewAdapter(personArrayList)
                    loading.visibility = View.GONE
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }



    }
}