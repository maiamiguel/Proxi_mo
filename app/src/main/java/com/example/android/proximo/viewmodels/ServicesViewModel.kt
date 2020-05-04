package com.example.android.proximo.viewmodels

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.*
import com.example.android.proximo.models.*
import com.example.android.proximo.network.Api
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE, NONE }

class ServicesViewModel(selectedTypesOfServices: Category, county: String, app: Application) : AndroidViewModel(app) {
    private val _status = MutableLiveData<ApiStatus>()

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
                _status.value = ApiStatus.ERROR
                Log.e("error", e.toString())
                _services.value = ArrayList()

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
