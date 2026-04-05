package com.example.aplikasiloginsederhana

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etEmail: EditText
    private lateinit var etTelepon: EditText
    private lateinit var etPassword: EditText
    private lateinit var etKonfirmasi: EditText
    private lateinit var rgJenisKelamin: RadioGroup
    private lateinit var btnDaftar: Button
    private lateinit var tvKembaliLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etNama          = findViewById(R.id.etNama)
        etEmail         = findViewById(R.id.etEmail)
        etTelepon       = findViewById(R.id.etTelepon)
        etPassword      = findViewById(R.id.etPassword)
        etKonfirmasi    = findViewById(R.id.etKonfirmasi)
        rgJenisKelamin  = findViewById(R.id.rgJenisKelamin)
        btnDaftar       = findViewById(R.id.btnDaftar)
        tvKembaliLogin  = findViewById(R.id.tvKembaliLogin)

        btnDaftar.setOnClickListener {
            val nama       = etNama.text.toString().trim()
            val email      = etEmail.text.toString().trim()
            val telepon    = etTelepon.text.toString().trim()
            val password   = etPassword.text.toString().trim()
            val konfirmasi = etKonfirmasi.text.toString().trim()

            // Validasi 1: semua field harus diisi
            if (nama.isEmpty() || email.isEmpty() || telepon.isEmpty()
                || password.isEmpty() || konfirmasi.isEmpty()) {
                showToast("Semua field harus diisi!")
                return@setOnClickListener
            }

            // Validasi 2: jenis kelamin harus dipilih
            // checkedRadioButtonId == -1 artinya belum ada yang dipilih
            if (rgJenisKelamin.checkedRadioButtonId == -1) {
                showToast("Pilih jenis kelamin terlebih dahulu!")
                return@setOnClickListener
            }

            // Validasi 3: password harus cocok
            if (password != konfirmasi) {
                showToast("Password dan konfirmasi tidak sama!")
                return@setOnClickListener
            }

            // Semua validasi lolos
            showToast("Registrasi berhasil! Selamat datang, $nama")

            // ─────────────────────────────────────────────
            // Setelah daftar sukses, tutup halaman ini dan
            // kembali otomatis ke halaman Login
            // finish() = hapus RegisterActivity dari stack
            // ─────────────────────────────────────────────
            finish()
        }

        // Tombol teks "Sudah punya akun?" → kembali ke Login
        tvKembaliLogin.setOnClickListener {
            finish() // cukup finish(), tidak perlu Intent baru
        }
    }

    private fun showToast(pesan: String) {
        Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show()
    }
}