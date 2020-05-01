package com.example.ubiquitousdroid.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.ubiquitousdroid.network.ApiInterface
import com.example.ubiquitousdroid.network.Resource
import com.example.ubiquitousdroid.network.RetrofitInstanceProvider
import kotlinx.coroutines.Dispatchers

class allImageViewModel : ViewModel() {
    private val apiinterface: ApiInterface =RetrofitInstanceProvider.getRetrofitInstance()
    fun getUsers() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiinterface.getAllImages()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "Error Occurred!"))
        }
    }
}