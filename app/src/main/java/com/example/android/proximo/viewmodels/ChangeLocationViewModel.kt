package com.example.android.proximo.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.proximo.network.Api
import kotlinx.coroutines.launch

enum class ChangeLocationStatus {LOCATING, ERROR, DONE}

class ChangeLocationViewModel : ViewModel() {
    private val _status = MutableLiveData<ChangeLocationStatus>()

    val status : LiveData<ChangeLocationStatus>
        get() = _status

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

    private fun getDistricts(){
        viewModelScope.launch {
            val getPropertiesDeferred =  Api.retrofitService.getDistrictsAsync()
            try {
                _status.value = ChangeLocationStatus.LOCATING
                val listResult = getPropertiesDeferred.await()
                _districts.value = listResult.districts
                Log.d("debug", "Districts ${listResult.districts}")
                _status.value = ChangeLocationStatus.DONE
            } catch (e: Exception) {
                Log.e("error", e.toString())
                _districts.value = ArrayList()
                _status.value = ChangeLocationStatus.ERROR
            }
        }
    }

    private fun getCounties(district: String){
        viewModelScope.launch {
            val getPropertiesDeferred =  Api.retrofitService.counties_by_districAsync(district)
            try {
                val listResult = getPropertiesDeferred.await()
                Log.d("debug", "Counties ${listResult.counties}")
                val countiesArray = ArrayList<String>()
                for (county in listResult.counties){
                    countiesArray.add(county[0])
                }
                _counties.value = countiesArray
            } catch (e: Exception) {
                Log.e("error", e.toString())
                _districts.value = ArrayList()
                _status.value = ChangeLocationStatus.ERROR
            }
        }
    }
}
