package com.example.android.proximo.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.android.proximo.models.*
import com.example.android.proximo.network.Api
import kotlinx.coroutines.launch

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

    private val companiesList = ArrayList<Company>()

    // Initialize the _selectedService MutableLiveData
    init {
        _selectedService.value = selectedTypesOfServices
        companies_by_location(selectedTypesOfServices, "Estarreja")
    }

    private fun companies_by_location(typeOfService : String, county : String){
        viewModelScope.launch {
            // Get the Deferred object for our Retrofit request
            val getPropertiesDeferred =  Api.retrofitService.companies_by_location(county)
            try {
                // this will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()



                for (company in listResult.companies){
                    Log.d("debug", "Aqui resultado : ${company.value}")
                    companiesList.add(company.value)
                }

                _services.value = companiesList
            } catch (e: Exception) {
                Log.e("error", e.toString())
//                _services.value = ArrayList()
            }
        }
    }

    fun displayServiceDetails(selectedCompany: Company) {
        _navigateToSelectedProperty.value = selectedCompany
    }

    fun displayServiceDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}
