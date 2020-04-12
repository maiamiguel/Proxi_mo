package com.example.android.proximo.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.proximo.models.Company
import com.example.android.proximo.models.TypesOfServices

/**
 *  The [ViewModel] associated with the [DetailFragment], containing information about the selected
 *  [TypesOfServices].
 */
class ServicesViewModel(selectedTypesOfServices: String, app: Application) : AndroidViewModel(app) {
    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedProperty = MutableLiveData<Company>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedProperty: LiveData<Company>
        get() = _navigateToSelectedProperty


    private val _selectedService = MutableLiveData<String>()
    // The external LiveData for the SelectedProperty
    val selectedTypesOfServices: LiveData<String>
        get() = _selectedService

    private val _services = MutableLiveData<List<Company>>()

    // The external LiveData for the SelectedProperty
    val properties: LiveData<List<Company>>
        get() = _services

    private val servicesList = ArrayList<Company>()

    // Initialize the _selectedService MutableLiveData
    init {
        _selectedService.value = selectedTypesOfServices

        servicesList.add(Company("TESTE","1", "teste"))
        servicesList.add(Company("TESTE","2", "teste"))
        servicesList.add(Company("TESTE","3", "teste"))
        servicesList.add(Company("TESTE","4", "teste"))

        _services.value = servicesList
    }

    fun displayServiceDetails(selectedCompany: Company) {
        _navigateToSelectedProperty.value = selectedCompany
    }

    fun displayServiceDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}
