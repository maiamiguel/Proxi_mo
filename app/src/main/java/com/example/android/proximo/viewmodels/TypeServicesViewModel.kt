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

class TypeServicesViewModel(county: String, app: Application) : AndroidViewModel(app) {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<MarsApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<MarsApiStatus>
        get() = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty
    // with new values
    private val _properties = MutableLiveData<List<Category>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val properties: LiveData<List<Category>>
        get() = _properties

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedProperty = MutableLiveData<Category>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedProperty: LiveData<Category>
        get() = _navigateToSelectedProperty

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        Log.d("debug", "COUNTY VIEDWMODEL FACTORY $county")

        getServicesCategories()
    }

    /**
     * Gets filtered Mars real estate property information from the Mars API Retrofit service and
     * updates the [MarsProperty] [List] and [MarsApiStatus] [LiveData]. The Retrofit service
     * returns a coroutine Deferred, which we await to get the result of the transaction.
     * @param filter the [MarsApiFilter] that is sent as part of the web server request
     */
    private fun getServicesCategories() {
        viewModelScope.launch {
            // Get the Deferred object for our Retrofit request
            val getPropertiesDeferred = Api.retrofitService.getServicesTypesAsync()
            try {
                _status.value = MarsApiStatus.LOADING
                val listResult = getPropertiesDeferred.await()
                _status.value = MarsApiStatus.DONE

                Log.d("debug", "HEREEE ${listResult.categories}")

                _properties.value = listResult.categories
            } catch (e: Exception) {
                Log.e("error", e.toString())
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedProperty] [MutableLiveData]
     * @param marsProperty The [MarsProperty] that was clicked on.
     */
    fun displayPropertyDetails(selectedTypesOfServices: Category) {
        _navigateToSelectedProperty.value = selectedTypesOfServices
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}