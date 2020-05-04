package com.example.android.proximo.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.android.proximo.models.Company

class SpecificViewModel(selectedCompanyArg: Company, app: Application) : AndroidViewModel(app) {
    var selectedCompany : Company = selectedCompanyArg
}
