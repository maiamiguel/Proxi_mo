package com.example.android.proximo.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.proximo.models.Service

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
