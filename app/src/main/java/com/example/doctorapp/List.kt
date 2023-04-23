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

                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }



    }

    private fun getPersonData() {
        personRecyclerView.visibility = View.GONE
        loading.visibility = View.VISIBLE

        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val ref = db.collection("user").document(userId)

        ref.get().addOnSuccessListener {
            if (it!= null){
                val name1 = it.data?.get("iname")?.toString()
                val date1 = it.data?.get("idate")?.toString()
                val phone1 = it.data?.get("inumber")?.toString()
                val desc1 = it.data?.get("idesc")?.toString()
                val gender1 = it.data?.get("igender")?.toString()

                personArrayList.add(PersonModel(name1!!, date1!!, phone1!!, desc1!!, gender1!!))
                personRecyclerView.adapter = RecycleViewAdapter(personArrayList)
                personRecyclerView.visibility = View.VISIBLE
                loading.visibility = View.GONE

            }
        }
    }
}