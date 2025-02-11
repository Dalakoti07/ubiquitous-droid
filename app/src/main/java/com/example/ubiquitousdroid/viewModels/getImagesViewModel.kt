package com.example.ubiquitousdroid.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.ubiquitousdroid.activitiesandfragments.MainActivity
import com.example.ubiquitousdroid.network.ApiInterface
import com.example.ubiquitousdroid.network.Resource
import com.example.ubiquitousdroid.network.RetrofitInstanceProvider
import kotlinx.coroutines.Dispatchers

class getImagesViewModel : ViewModel() {
    fun getImages() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = MainActivity.apiinterface.getAllImages("Client-ID a73c8ca2ffde994")))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "Error Occurred!"))
        }
    }

    fun getUploadedImages() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = MainActivity.apiinterface.getImagesFromGallery("Client-ID a73c8ca2ffde994")))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "Error Occurred!"))
        }
    }
}