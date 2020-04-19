package com.example.android.proximo.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.proximo.network.Api
import kotlinx.coroutines.launch

enum class LocationStatus {LOCATING, ERROR, DONE}

class LocationViewModel : ViewModel() {
    // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty
    // with new values
    private val _properties = MutableLiveData<List<String>>()
    // The external LiveData interface to the property is immutable, so only this class can modify
    val properties: LiveData<List<String>>
        get() = _properties

    private val _countiesByDistrict = MutableLiveData<List<List<String>>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val countiesByDistrict: LiveData<List<List<String>>>
        get() = _countiesByDistrict

    private val _location = MutableLiveData<LocationStatus>()

    val location : LiveData<LocationStatus>
        get() = _location

    private val _doneLocating = MutableLiveData<Boolean>()

    val doneLocating : LiveData<Boolean>
        get() = _doneLocating

    init {
        getServicesCategories()
    }

    private fun getServicesCategories() {
        viewModelScope.launch {
            // Get the Deferred object for our Retrofit request
            val categoriesServices =  Api.retrofitService.getDistrictsAsync()
            try {
                // this will run on a thread managed by Retrofit
                val listResult = categoriesServices.await()
                _properties.value = listResult.districts
            } catch (e: Exception) {
                Log.e("error", e.toString())
                _properties.value = ArrayList()
            }
        }
    }

    fun setError(){
        _location.value = LocationStatus.ERROR
    }

    fun locating(){
        _location.value = LocationStatus.LOCATING
    }

    fun doneLocating(){
        _location.value = LocationStatus.DONE
        _doneLocating.value = true
    }

    fun searchCounties(district : String){
        counties_by_distric(district)
    }

    private fun counties_by_distric(district: String){
        viewModelScope.launch {
            // Get the Deferred object for our Retrofit request
            val getPropertiesDeferred =  Api.retrofitService.counties_by_districAsync(district)
            try {
                // this will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()
                _countiesByDistrict.value = listResult.counties
                Log.d("debug", "Locations ${listResult.counties}")
                for (county in listResult.counties){
                    if (county[0] == "Estarreja"){
                        Log.d("debug", "VOILÀ")
                    }
                }
            } catch (e: Exception) {
                Log.e("error", e.toString())
                _properties.value = ArrayList()
            }
        }
    }
}
