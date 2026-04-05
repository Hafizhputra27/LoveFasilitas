package com.example.lovefasilitas

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var tvUsername: TextView
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize Views
        tvUsername = findViewById(R.id.tvUsername)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        // Receive username from Intent extras
        val username = intent.getStringExtra("username") ?: "Guest"

        // Display username
        tvUsername.text = "Username: $username"

        // Set up bottom navigation menu item click listener
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_history -> {
                    Toast.makeText(this, "History Sewa", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_profile -> {
                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        // Set default selected item
        bottomNavigation.selectedItemId = R.id.menu_home
    }
}
