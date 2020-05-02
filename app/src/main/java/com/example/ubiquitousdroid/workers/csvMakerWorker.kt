package com.example.ubiquitousdroid.workers

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.ubiquitousdroid.activitiesandfragments.contactFragment.Companion.allContacts
import com.example.ubiquitousdroid.activitiesandfragments.contactFragment.Companion.filrName
import com.example.ubiquitousdroid.models.contactObject
import com.example.ubiquitousdroid.utils.*
import java.io.File
import java.io.FileOutputStream

class csvMakerWorker (ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        val appContext = applicationContext
        val directoryDownload: File =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        makeStatusNotification("Making CSV ", appContext)
        sleep()

        val file = File(directoryDownload, filrName)
        var outputStream: FileOutputStream? = null

        return try {
            outputStream = FileOutputStream(file, true)
            //outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            var i = 0
            while (i < allContacts.size) {
                Log.d("Commontag"," writing "+(i+1).toString())
                outputStream.write(((i+1).toString() + ",").toByteArray())
                outputStream.write((allContacts.get(i).name + ",").toByteArray())
                outputStream.write((allContacts.get(i).number + "\n").toByteArray())
                i += 1
            }
            outputStream.close()

//            fileUtil.zipTheFiles(listOf(filrName), "zippedContactfile.zip")
            val outputData = workDataOf(KEY_IMAGE_URI to "zippedContactfile.zip",KEY_FILE_TO_BE_ZIPPED to filrName)

            Result.success(outputData)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

}