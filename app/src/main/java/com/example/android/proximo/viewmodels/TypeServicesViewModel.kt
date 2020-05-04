package com.example.android.proximo.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.android.proximo.models.Category
import com.example.android.proximo.network.Api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

enum class MarsApiStatus { LOADING, ERROR, DONE }

class TypeServicesViewModel(county: String, district: String, app: Application) : AndroidViewModel(app) {
    private val _status = MutableLiveData<MarsApiStatus>()

    val status: LiveData<MarsApiStatus>
        get() = _status

    private val _properties = MutableLiveData<List<Category>>()

    val properties: LiveData<List<Category>>
        get() = _properties

    private val _navigateToSelectedProperty = MutableLiveData<Category>()

    val navigateToSelectedProperty: LiveData<Category>
        get() = _navigateToSelectedProperty

    init {
        getServicesCategories()
    }

    private fun getServicesCategories() {
        viewModelScope.launch {
            val getPropertiesDeferred = Api.retrofitService.getServicesTypesAsync()
            try {
                _status.value = MarsApiStatus.LOADING
                val listResult = getPropertiesDeferred.await()
                _status.value = MarsApiStatus.DONE

                Log.d("debug", "${listResult.categories}")

                _properties.value = listResult.categories
            } catch (e: Exception) {
                Log.e("error", e.toString())
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    fun displayPropertyDetails(selectedTypesOfServices: Category) {
        _navigateToSelectedProperty.value = selectedTypesOfServices
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}