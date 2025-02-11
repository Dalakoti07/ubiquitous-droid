package com.example.ubiquitousdroid.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import com.example.ubiquitousdroid.models.contactObject
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


class fileUtil {
    companion object{
        val BUFFER =4
        val directoryDownload: File =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        fun saveAFileWithName(filrName:String,list:List<contactObject>){
            val file = File(directoryDownload, filrName)
            var outputStream: FileOutputStream? = null
            try {
                outputStream = FileOutputStream(file, true)
                //outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                var i = 0
                while (i < list.size) {
                    Log.d("Commontag"," writing "+(i+1).toString())
                    outputStream.write(((i+1).toString() + ",").toByteArray())
                    outputStream.write((list.get(i).name + ",").toByteArray())
                    outputStream.write((list.get(i).number + "\n").toByteArray())
                    i += 1
                }
                outputStream.close()
                zipTheFiles(listOf(filrName),"zippedContactfile.zip")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun zipTheFiles(listOfFiles :List<String>,ZipFileName:String){
            try {
                var origin: BufferedInputStream? = null
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
                        ZipEntry(listOfFiles.get(i).substring(listOfFiles.get(i).lastIndexOf("/") + 1))
                    out.putNextEntry(entry)
                    var count: Int=0
                    while (origin.read(data, 0, BUFFER).also({ count = it }) != -1) {
                        out.write(data, 0, count)
                    }
                    origin.close()
                }
                Log.d("commontag", "compression complete")
                out.close()
            } catch (e: java.lang.Exception) {
                Log.d("commontag", "compression failed")
                e.printStackTrace()
            }
        }

        fun saveAnImaage(context: Context,image : Bitmap):String{
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            if(storageDir!=null){
                val imageFile=File(storageDir,"JPEG_${timeStamp}_.jpg")
                val outputStream = FileOutputStream(imageFile, false)
                image.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                return imageFile.absolutePath
            }else{
                return ""
            }
        }
    }
}