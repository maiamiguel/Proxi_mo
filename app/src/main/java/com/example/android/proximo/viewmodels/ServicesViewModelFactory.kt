package com.example.android.proximo.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.proximo.models.Category

class ServicesViewModelFactory(
        private val selectedTypesOfServices: Category,
        private val county : String,
        private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServicesViewModel::class.java)) {
            return ServicesViewModel(selectedTypesOfServices, county, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
