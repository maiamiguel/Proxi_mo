package com.example.android.proximo.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.proximo.R
import com.example.android.proximo.models.TypesOfServices
import com.example.android.proximo.network.MarsApiFilter
import com.example.android.proximo.network.MarsProperty

enum class MarsApiStatus { LOADING, ERROR, DONE }
/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<MarsApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<MarsApiStatus>
        get() = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty
    // with new values
    private val _properties = MutableLiveData<List<TypesOfServices>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val properties: LiveData<List<TypesOfServices>>
        get() = _properties

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedProperty = MutableLiveData<TypesOfServices>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedProperty: LiveData<TypesOfServices>
        get() = _navigateToSelectedProperty

//    // Create a Coroutine scope using a job to be able to cancel when needed
//    private var viewModelJob = Job()
//
//    // the Coroutine runs using the Main (UI) dispatcher
//    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val servicesList = ArrayList<TypesOfServices>()

    private val serviceTitles = arrayOf("Supermercados", "Farmácias", "Correios", "Take-Away")
    private val images = arrayOf(
            R.drawable.ic_broken_image,
            R.drawable.ic_launcher_background,
            R.drawable.common_google_signin_btn_icon_light_focused,
            R.drawable.common_google_signin_btn_icon_dark,
            R.drawable.common_google_signin_btn_icon_disabled
    )

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        //getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
        _status.value = MarsApiStatus.DONE

        for (i in serviceTitles.indices) {
            servicesList.add((TypesOfServices(serviceTitles[i] , images[i])))
        }

        _properties.value = servicesList
    }

//    /**
//     * Gets filtered Mars real estate property information from the Mars API Retrofit service and
//     * updates the [MarsProperty] [List] and [MarsApiStatus] [LiveData]. The Retrofit service
//     * returns a coroutine Deferred, which we await to get the result of the transaction.
//     * @param filter the [MarsApiFilter] that is sent as part of the web server request
//     */
//     private fun getMarsRealEstateProperties(filter: MarsApiFilter) {
//        coroutineScope.launch {
//            // Get the Deferred object for our Retrofit request
//            var getPropertiesDeferred = MarsApi.retrofitService.getProperties(filter.value)
//            try {
//                _status.value = MarsApiStatus.LOADING
//                // this will run on a thread managed by Retrofit
//                val listResult = getPropertiesDeferred.await()
//                _status.value = MarsApiStatus.DONE
//                _properties.value = listResult
//            } catch (e: Exception) {
//                _status.value = MarsApiStatus.ERROR
//                _properties.value = ArrayList()
//            }
//        }
//    }

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        //viewModelJob.cancel()
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedProperty] [MutableLiveData]
     * @param marsProperty The [MarsProperty] that was clicked on.
     */
    fun displayPropertyDetails(selectedTypesOfServices: TypesOfServices) {
        _navigateToSelectedProperty.value = selectedTypesOfServices
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    /**
     * Updates the data set filter for the web services by querying the data with the new filter
     * by calling [getMarsRealEstateProperties]
     * @param filter the [MarsApiFilter] that is sent as part of the web server request
     */
    fun updateFilter(filter: MarsApiFilter) {
        //getMarsRealEstateProperties(filter)
    }
}