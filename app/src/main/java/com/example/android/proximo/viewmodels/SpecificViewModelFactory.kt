package com.example.android.proximo.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.proximo.models.Company

class SpecificViewModelFactory(
        private val selectedCompany: Company,
        private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpecificViewModel::class.java)) {
            return SpecificViewModel(selectedCompany, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
