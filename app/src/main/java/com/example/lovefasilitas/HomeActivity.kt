package com.example.lovefasilitas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lovefasilitas.ui.chat.ChatFragment
import com.example.lovefasilitas.ui.history.HistoryFragment
import com.example.lovefasilitas.ui.home.HomeFragment
import com.example.lovefasilitas.ui.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigation = findViewById(R.id.bottomNavigation)

        if (savedInstanceState == null) {
            val homeFragment = HomeFragment()
            val username = intent.getStringExtra(EXTRA_USERNAME) ?: "User"
            homeFragment.arguments = Bundle().apply {
                putString(EXTRA_USERNAME, username)
            }
            switchFragment(homeFragment)
            bottomNavigation.selectedItemId = R.id.menu_home
        }

        bottomNavigation.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.menu_home -> HomeFragment()
                R.id.menu_chat -> ChatFragment()
                R.id.menu_history -> HistoryFragment()
                R.id.menu_profile -> ProfileFragment()
                else -> return@setOnItemSelectedListener false
            }
            switchFragment(fragment)
            true
        }
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    companion object {
        const val EXTRA_USERNAME = "username"
    }
}
