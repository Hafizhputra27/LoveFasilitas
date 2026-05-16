package com.example.lovefasilitas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.example.lovefasilitas.ui.login.LoginViewModel
import com.example.lovefasilitas.ui.login.LoginViewModelFactory
import com.example.lovefasilitas.domain.usecase.LoginUseCase

class MainActivity : AppCompatActivity() {

    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: View
    private lateinit var tvRegister: TextView
    private lateinit var loadingOverlay: View

    private val viewModel: LoginViewModel by lazy {
        val app = application as LoveFasilitasApp
        val loginUseCase = LoginUseCase(app.container.userRepository)
        ViewModelProvider(this, LoginViewModelFactory(loginUseCase))[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tilEmail = findViewById(R.id.tilEmail)
        tilPassword = findViewById(R.id.tilPassword)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegister = findViewById(R.id.tvRegister)
        loadingOverlay = findViewById(R.id.loadingOverlay)

        observeViewModel()

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            var hasError = false
            if (email.isEmpty()) {
                tilEmail.error = getString(R.string.validation_email_empty)
                hasError = true
            } else {
                tilEmail.error = null
            }
            if (password.isEmpty()) {
                tilPassword.error = getString(R.string.validation_password_empty)
                hasError = true
            } else {
                tilPassword.error = null
            }
            if (hasError) return@setOnClickListener

            viewModel.login(email, password)
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { loading ->
            loadingOverlay.visibility = if (loading) View.VISIBLE else View.GONE
            btnLogin.isEnabled = !loading
        }

        viewModel.navigationEvent.observe(this) { event ->
            event.getContentIfNotHandled()?.let { user ->
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra(HomeActivity.EXTRA_USERNAME, user.name)
                startActivity(intent)
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
