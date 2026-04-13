package com.example.lovefasilitas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class EventListActivity : AppCompatActivity() {

    private lateinit var rvEvents: RecyclerView
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)

        rvEvents = findViewById(R.id.rvEvents)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        // 1. Data Statis (Hardcoded)
        val list = listOf(
            Event(1, "Konser Musik Tulus", "12 Des 2024", "Stadion Utama Gelora Bung Karno", 150000),
            Event(2, "Workshop Android Kotlin", "15 Des 2024", "Gedung Serbaguna Kampus", 0),
            Event(3, "Pameran Seni Modern", "20 Des 2024", "Galeri Nasional Indonesia", 50000),
            Event(4, "Seminar Startup Digital", "22 Des 2024", "Hotel Indonesia Kempinski", 0),
            Event(5, "Festival Kuliner Nusantara", "25 Des 2024", "Parkir Timur Senayan", 25000)
        )

        // 2. Setup RecyclerView
        rvEvents.layoutManager = LinearLayoutManager(this)
        val adapter = EventAdapter(list) { event ->
            Toast.makeText(this, "Event: ${event.name}", Toast.LENGTH_SHORT).show()
        }
        rvEvents.adapter = adapter

        // 3. Logika BottomNavigationView
        bottomNavigation.selectedItemId = R.id.menu_history // Asumsi ini adalah halaman history/event
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    // Berpindah kembali ke HomeActivity
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.menu_history -> true
                R.id.menu_profile -> {
                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
}
