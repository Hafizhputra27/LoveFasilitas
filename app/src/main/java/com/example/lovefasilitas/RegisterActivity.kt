package com.example.lovefasilitas

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.example.lovefasilitas.domain.usecase.RegisterUserUseCase
import com.example.lovefasilitas.ui.register.RegisterViewModel
import com.example.lovefasilitas.ui.register.RegisterViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var etNama: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etTelepon: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etKonfirmasi: TextInputEditText
    private lateinit var btnDaftar: View
    private lateinit var tvKembaliLogin: View
    private lateinit var loadingOverlay: View
    private lateinit var btnBack: View

    private val viewModel: RegisterViewModel by lazy {
        val app = application as LoveFasilitasApp
        val registerUserUseCase = RegisterUserUseCase(app.container.userRepository)
        ViewModelProvider(this, RegisterViewModelFactory(registerUserUseCase))[RegisterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etNama = findViewById(R.id.etNama)
        etEmail = findViewById(R.id.etEmail)
        etTelepon = findViewById(R.id.etTelepon)
        etPassword = findViewById(R.id.etPassword)
        etKonfirmasi = findViewById(R.id.etKonfirmasi)
        btnDaftar = findViewById(R.id.btnDaftar)
        tvKembaliLogin = findViewById(R.id.tvKembaliLogin)
        loadingOverlay = findViewById(R.id.loadingOverlay)
        btnBack = findViewById(R.id.btnBack)

        observeViewModel()

        btnBack.setOnClickListener { finish() }

        btnDaftar.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val telepon = etTelepon.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val konfirmasi = etKonfirmasi.text.toString().trim()

            if (nama.isEmpty() || email.isEmpty() || password.isEmpty() || konfirmasi.isEmpty()) {
                Snackbar.make(findViewById(android.R.id.content), R.string.msg_empty_field, Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != konfirmasi) {
                Snackbar.make(findViewById(android.R.id.content), R.string.msg_password_mismatch, Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.register(email, nama, password, telepon)
        }

        tvKembaliLogin.setOnClickListener { finish() }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { loading ->
            loadingOverlay.visibility = if (loading) View.VISIBLE else View.GONE
            btnDaftar.isEnabled = !loading
        }

        viewModel.navigationEvent.observe(this) { event ->
            event.getContentIfNotHandled()?.let { user ->
                Snackbar.make(findViewById(android.R.id.content), getString(R.string.msg_registration_success, user.name), Snackbar.LENGTH_SHORT).show()
                finish()
            }
        }

        viewModel.errorEvent.observe(this) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
