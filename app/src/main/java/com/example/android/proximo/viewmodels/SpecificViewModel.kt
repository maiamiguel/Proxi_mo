package com.example.android.proximo.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.proximo.models.Company

class SpecificViewModel(selectedCompanyArg: Company, app: Application) : AndroidViewModel(app) {
    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedProperty = MutableLiveData<Company>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedProperty: LiveData<Company>
        get() = _navigateToSelectedProperty

    var selectedCompany : Company = selectedCompanyArg

    fun displayServiceDetails(selectedCompany: Company) {
        _navigateToSelectedProperty.value = selectedCompany
    }

    fun displayServiceDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}
