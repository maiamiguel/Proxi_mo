package com.example.android.marsrealestate.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.R
import com.example.android.marsrealestate.models.Service
import com.example.android.marsrealestate.network.MarsProperty

/**
 *  The [ViewModel] associated with the [DetailFragment], containing information about the selected
 *  [Service].
 */
class DetailViewModel(selectedService: Service, app: Application) : AndroidViewModel(app) {
    private val _selectedService = MutableLiveData<Service>()

    // The external LiveData for the SelectedProperty
    val selectedService: LiveData<Service>
        get() = _selectedService

    // Initialize the _selectedService MutableLiveData
    init {
        _selectedService.value = selectedService
    }
}
