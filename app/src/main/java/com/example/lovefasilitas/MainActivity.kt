package com.example.lovefasilitas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvDaftar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin   = findViewById(R.id.btnLogin)
        tvDaftar   = findViewById(R.id.tvDaftar)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    "Username dan Password tidak boleh kosong!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Simple hardcoded login check for demonstration
                if (username == "admin" && password == "admin123") {
                    Toast.makeText(this, "Anda berhasil login!", Toast.LENGTH_SHORT).show()

                    // Create Explicit Intent to HomeActivity
                    val intent = Intent(this, HomeActivity::class.java)
                    // Pass username data using putExtra()
                    intent.putExtra("username", username)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Username atau Password salah!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        tvDaftar.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
