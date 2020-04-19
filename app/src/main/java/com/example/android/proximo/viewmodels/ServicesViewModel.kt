package com.example.android.proximo.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.android.proximo.models.*
import com.example.android.proximo.network.Api
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE, NONE }

class ServicesViewModel(selectedTypesOfServices: Category, county: String, app: Application) : AndroidViewModel(app) {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus>
        get() = _status

    private val _navigateToSelectedProperty = MutableLiveData<Company>()

    val navigateToSelectedProperty: LiveData<Company>
        get() = _navigateToSelectedProperty


    private val _services = MutableLiveData<List<Company>>()
    val properties: LiveData<List<Company>>
        get() = _services

    private val companiesList = ArrayList<Company>()

    init {
        companies_by_location(selectedTypesOfServices, county)
    }

    private fun companies_by_location(typeOfService : Category, county : String){
        viewModelScope.launch {
            // Get the Deferred object for our Retrofit request
            val getPropertiesDeferred =  Api.retrofitService.companies_by_locationAsync(county)
            try {
                _status.value = ApiStatus.LOADING
                val listResult = getPropertiesDeferred.await()

                for (company in listResult.companies){
                    for (category in company.value.categories){
                        // TODO: CHANGE THIS
                        if (typeOfService.category == category){
                            companiesList.add(company.value)
                        }
                    }
                }

                _services.value = companiesList

                if (companiesList.size > 0){
                    _status.value = ApiStatus.DONE
                }
                else{
                    _status.value = ApiStatus.NONE
                }
            } catch (e: Exception) {
                Log.e("error", e.toString())
//                _services.value = ArrayList()
                _status.value = ApiStatus.ERROR
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
