package com.example.ubiquitousdroid.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.ubiquitousdroid.activitiesandfragments.MainActivity
import com.example.ubiquitousdroid.network.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class UploadFileViewModel : ViewModel() {
    fun uploadImage(imagePath: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val fileToBeUploaded = File(imagePath)

            val requestFile: RequestBody = RequestBody.create(MediaType.parse("image/jpeg"),fileToBeUploaded)
            val mulitpartFile =
                MultipartBody.Part.createFormData("image", fileToBeUploaded.name, requestFile)
            emit(Resource.success(data = MainActivity.apiinterface.postAnImage(
                "Bearer 9f0d3e0621457e30a08ef00dfc3c5281c10ae2ac",
                mulitpartFile,
                RequestBody.create(MediaType.parse("text/plain"),"ogCGesN"),
                RequestBody.create(MediaType.parse("text/plain"),"file"),
                RequestBody.create(MediaType.parse("text/plain"),fileToBeUploaded.name),
                RequestBody.create(MediaType.parse("text/plain"),"some title"),
                RequestBody.create(MediaType.parse("text/plain"),fileToBeUploaded.name+" description "),
                RequestBody.create(MediaType.parse("text/plain"),"1"))))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "Error Occurred!"))
        }
    }
}