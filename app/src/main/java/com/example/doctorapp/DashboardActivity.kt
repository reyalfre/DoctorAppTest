package com.example.doctorapp

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DashboardActivity : AppCompatActivity() {
    var searchdocter: EditText? = null
    var searchbtn: ImageButton? = null
    var cough: ImageButton? = null
    var teeth: ImageButton? = null
    var eye: ImageButton? = null
    var fracture: ImageButton? = null
    var menubtn : ImageButton? = null
    private var pressedTime: Long = 0
    var drawerLayout: DrawerLayout? = null
    var toolbar: Toolbar? = null
    var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    var navView : NavigationView? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(toolbar)
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer)
        searchdocter = findViewById(R.id.searchdoctor)
        searchbtn = findViewById(R.id.searchbtn)
        cough = findViewById(R.id.cough)
        teeth = findViewById(R.id.teeth)
        eye = findViewById(R.id.eye)
        fracture = findViewById(R.id.fracture)
        menubtn = findViewById(R.id.menubtn)
        navView = findViewById(R.id.navigationview)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)



        searchbtn!!.setOnClickListener(View.OnClickListener {
            val words = searchdocter!!.text.toString()
            searchNet(words)
        })
        cough!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Form::class.java)
            startActivity(intent)
        })
        teeth!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Form::class.java)
            startActivity(intent)
        })
        eye!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Form::class.java)
            startActivity(intent)
        })
        fracture!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Form::class.java)
            startActivity(intent)
        })
        menubtn!!.setOnClickListener {
            actionBarDrawerToggle =
                ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        }
        navView!!.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.exit -> {
                    Firebase.auth.signOut()
                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                }

            }
            true
        }

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    override fun onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finish()
        } else {
            Toast.makeText(baseContext, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
        pressedTime = System.currentTimeMillis()
    }

    private fun searchNet(words: String) {
        try {
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.putExtra(SearchManager.QUERY, words)
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            searchNetCompat(words)
        }
    }

    private fun searchNetCompat(words: String) {
        try {
            val uri = Uri.parse("https://www.google.com/search?q=$words")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}