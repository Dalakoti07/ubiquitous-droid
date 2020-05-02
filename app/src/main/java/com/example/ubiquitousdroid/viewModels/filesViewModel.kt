package com.example.ubiquitousdroid.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.*
import com.example.ubiquitousdroid.utils.Files_MANIPULATION_WORK_NAME
import com.example.ubiquitousdroid.utils.TAG_OUTPUT
import com.example.ubiquitousdroid.workers.csvMakerWorker
import com.example.ubiquitousdroid.workers.zipWorker
import kotlinx.coroutines.channels.consumesAll

class filesViewModel (application: Application) : AndroidViewModel(application) {
    private val workManager = WorkManager.getInstance(application)
    internal val outputWorkInfos: LiveData<List<WorkInfo>>

    init {
        // This transformation makes sure that whenever the current work Id changes the WorkInfo
        // the UI is listening to changes
        outputWorkInfos = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)
    }

    internal fun GenerateAndZipFile(){
        var continuation = workManager
            .beginUniqueWork(
                Files_MANIPULATION_WORK_NAME,
                ExistingWorkPolicy.KEEP,
                OneTimeWorkRequest.from(csvMakerWorker::class.java)
            )
        val zipWorkerTask = OneTimeWorkRequestBuilder<zipWorker>()
            .addTag(TAG_OUTPUT)
            .build()
         continuation= continuation.then(zipWorkerTask)

        //start
        continuation.enqueue()
    }
}