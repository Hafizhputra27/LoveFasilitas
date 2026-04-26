package com.example.lovefasilitas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigation = findViewById(R.id.bottomNavigation)

        if (savedInstanceState == null) {
            switchFragment(HomeFragment())
            bottomNavigation.selectedItemId = R.id.menu_home
        }

        bottomNavigation.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.menu_home -> HomeFragment()
                R.id.menu_chat -> ChatFragment()
                R.id.menu_history -> HistoryFragment()
                R.id.menu_profile -> ProfileFragment()
                else -> null
            }

            fragment?.let {
                switchFragment(it)
                true
            } ?: false
        }
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
