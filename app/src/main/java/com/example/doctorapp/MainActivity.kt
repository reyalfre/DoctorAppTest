package com.example.doctorapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var getstarted: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getstarted = findViewById(R.id.getstarted)
        getstarted!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        })
    }
}