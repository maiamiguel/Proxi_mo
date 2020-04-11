package com.example.android.proximo.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.proximo.models.Service

class SpecificViewModel(selectedTypesOfServices: Service, app: Application) : AndroidViewModel(app) {
    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedProperty = MutableLiveData<Service>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedProperty: LiveData<Service>
        get() = _navigateToSelectedProperty

    private val _selectedService = MutableLiveData<String>()
    // The external LiveData for the SelectedProperty
    val selectedTypesOfServices: LiveData<String>
        get() = _selectedService


    init {

    }

    fun displayServiceDetails(selectedService: Service) {
        _navigateToSelectedProperty.value = selectedService
    }

    fun displayServiceDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}
