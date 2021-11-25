package com.demo.astronomy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.astronomy.model.APODModel
import com.demo.astronomy.repository.APODRepository

/**
 * ViewModel
 */
class MainActivityViewModel : ViewModel() {


    private var servicesLiveData: MutableLiveData<APODModel>? = null

    fun getAPODModel(): LiveData<APODModel>? {
        servicesLiveData = APODRepository.getAPODServiceApiCall()
        return servicesLiveData
    }
}