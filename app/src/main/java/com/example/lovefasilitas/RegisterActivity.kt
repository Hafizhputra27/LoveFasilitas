package com.example.lovefasilitas

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

            // Validasi 1: Nama tidak boleh kosong
            if (nama.isEmpty()) {
                showToast("Nama tidak boleh kosong!")
                return@setOnClickListener
            }

            // Validasi 2: Email tidak boleh kosong
            if (email.isEmpty()) {
                showToast("Email tidak boleh kosong!")
                return@setOnClickListener
            }

            // Validasi 3: Email format valid
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                showToast("Format email tidak valid!")
                return@setOnClickListener
            }

            // Validasi 4: Telepon tidak boleh kosong
            if (telepon.isEmpty()) {
                showToast("Nomor telepon tidak boleh kosong!")
                return@setOnClickListener
            }

            // Validasi 5: Password tidak boleh kosong
            if (password.isEmpty()) {
                showToast("Password tidak boleh kosong!")
                return@setOnClickListener
            }

            // Validasi 6: Konfirmasi password tidak boleh kosong
            if (konfirmasi.isEmpty()) {
                showToast("Konfirmasi password tidak boleh kosong!")
                return@setOnClickListener
            }

            // Validasi 7: Jenis kelamin harus dipilih
            if (rgJenisKelamin.checkedRadioButtonId == -1) {
                showToast("Pilih jenis kelamin terlebih dahulu!")
                return@setOnClickListener
            }

            // Validasi 8: Password harus cocok
            if (password != konfirmasi) {
                showToast("Password dan konfirmasi tidak sama!")
                return@setOnClickListener
            }

            // Semua validasi lolos
            showToast("Registrasi berhasil! Selamat datang, $nama")

            // Kembali ke halaman Login setelah registrasi sukses
            finish()
        }

        // Tombol teks "Sudah punya akun?" → kembali ke Login
        tvKembaliLogin.setOnClickListener {
            finish()
        }
    }

    private fun showToast(pesan: String) {
        Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show()
    }
}