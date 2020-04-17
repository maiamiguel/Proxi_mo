package com.example.android.proximo.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.proximo.models.Company

class TypeServiceViewModelFactory(
        private val county: String,
        private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TypeServicesViewModel::class.java)) {
            return TypeServicesViewModel(county, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}