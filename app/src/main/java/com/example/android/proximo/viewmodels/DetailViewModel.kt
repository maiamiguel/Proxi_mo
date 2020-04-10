package com.example.android.proximo.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.proximo.models.Service
import com.example.android.proximo.models.TypesOfServices

/**
 *  The [ViewModel] associated with the [DetailFragment], containing information about the selected
 *  [TypesOfServices].
 */
class DetailViewModel(selectedTypesOfServices: String, app: Application) : AndroidViewModel(app) {
    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedProperty = MutableLiveData<Service>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedProperty: LiveData<Service>
        get() = _navigateToSelectedProperty


    private val _selectedService = MutableLiveData<String>()
    // The external LiveData for the SelectedProperty
    val selectedTypesOfServices: LiveData<String>
        get() = _selectedService

    private val _services = MutableLiveData<List<Service>>()

    // The external LiveData for the SelectedProperty
    val properties: LiveData<List<Service>>
        get() = _services

    private val servicesList = ArrayList<Service>()

    // Initialize the _selectedService MutableLiveData
    init {
        _selectedService.value = selectedTypesOfServices

        servicesList.add(Service("TESTE","1"))
        servicesList.add(Service("TESTE","2"))
        servicesList.add(Service("TESTE","3"))
        servicesList.add(Service("TESTE","4"))

        _services.value = servicesList
    }

    fun displayServiceDetails(selectedService: Service) {
        _navigateToSelectedProperty.value = selectedService
    }

    fun displayServiceDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}
