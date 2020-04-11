package com.example.android.proximo.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ServicesViewModelFactory(
        private val selectedTypesOfServices: String,
        private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServicesViewModel::class.java)) {
            return ServicesViewModel(selectedTypesOfServices, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
