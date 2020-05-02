package com.example.ubiquitousdroid.workers

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.ubiquitousdroid.models.contactObject
import com.example.ubiquitousdroid.utils.*
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class zipWorker (ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        val BUFFER =4
        val appContext = applicationContext
        val directoryDownload: File =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        makeStatusNotification("Zipping the file ", appContext)
        sleep()

        return try {
            var origin: BufferedInputStream? = null
            val ZipFileName = inputData.getString(KEY_IMAGE_URI)
            val listOfFiles = listOf<String?>(inputData.getString(KEY_FILE_TO_BE_ZIPPED))
            val dest = FileOutputStream(File(directoryDownload,ZipFileName))
            val out = ZipOutputStream(
                BufferedOutputStream(
                    dest
                )
            )
            val data = ByteArray(BUFFER)
            for (i in 0 until listOfFiles.size) {
                Log.d("commontag", "compressing Adding: " + listOfFiles.get(i))
                val fi = FileInputStream(File(directoryDownload,listOfFiles.get(i)))
                origin = BufferedInputStream(fi, BUFFER)
                val entry =
                    ZipEntry(listOfFiles.get(i)?.substring(listOfFiles.get(i)!!.lastIndexOf("/") + 1))
                out.putNextEntry(entry)
                var count: Int=0
                while (origin.read(data, 0, BUFFER).also({ count = it }) != -1) {
                    out.write(data, 0, count)
                }
                origin.close()
            }
            Log.d("commontag", "compression complete")
            out.close()
            Result.success()

        } catch (e: java.lang.Exception) {
            Log.d("commontag", "compression failed")
            e.printStackTrace()
            Result.failure()
        }
    }

}