package com.example.android.proximo.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.proximo.network.Api
import kotlinx.coroutines.launch

class ChangeLocationViewModel : ViewModel() {

    private val _districts = MutableLiveData<List<String>>()
    val districts: LiveData<List<String>>
        get() = _districts

    private val _counties = MutableLiveData<List<String>>()
    val counties: LiveData<List<String>>
        get() = _counties

    init{
        getDistricts()
    }

    fun searchCounties(district : String){
        getCounties(district)
    }

    private fun getCounties(district: String){
        viewModelScope.launch {
            // Get the Deferred object for our Retrofit request
            val getPropertiesDeferred =  Api.retrofitService.counties_by_districAsync(district)
            try {
                // this will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()
                //_counties.value = listResult.counties
                Log.d("debug", "Counties ${listResult.counties}")
                val countiesArray = ArrayList<String>()
                for (county in listResult.counties){
                    countiesArray.add(county[0])
                }
                _counties.value = countiesArray
            } catch (e: Exception) {
                Log.e("error", e.toString())
                _districts.value = ArrayList()
            }
        }
    }

    private fun getDistricts(){
        viewModelScope.launch {
            // Get the Deferred object for our Retrofit request
            val getPropertiesDeferred =  Api.retrofitService.getDistrictsAsync()
            try {
                // this will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()
                _districts.value = listResult.districts
                Log.d("debug", "Districts ${listResult.districts}")
//                for (county in listResult.counties){
//                    if (county[0] == "Estarreja"){
//                        Log.d("debug", "VOILÀ")
//                    }
//                }
            } catch (e: Exception) {
                Log.e("error", e.toString())
                _districts.value = ArrayList()
            }
        }
    }
}
