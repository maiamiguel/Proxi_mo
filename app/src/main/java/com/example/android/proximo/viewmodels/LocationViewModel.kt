package com.example.android.proximo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class LocationStatus {LOCATING, ERROR, DONE}

class LocationViewModel : ViewModel() {
    private val _location = MutableLiveData<LocationStatus>()

    val location : LiveData<LocationStatus>
        get() = _location

    private val _doneLocating = MutableLiveData<Boolean>()

    val doneLocating : LiveData<Boolean>
        get() = _doneLocating

    init {
        _doneLocating.value = false
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
}
