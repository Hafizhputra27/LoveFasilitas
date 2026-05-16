package com.example.lovefasilitas.ui.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lovefasilitas.LoveFasilitasApp

abstract class BaseFragment<VM : ViewModel> : Fragment() {

    protected abstract val viewModelClass: Class<VM>

    protected val viewModel: VM by lazy {
        ViewModelProvider(this)[viewModelClass]
    }

    protected val appContainer by lazy {
        (requireActivity().application as LoveFasilitasApp).container
    }
}