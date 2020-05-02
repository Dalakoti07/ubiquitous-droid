package com.example.ubiquitousdroid.activitiesandfragments

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.ubiquitousdroid.R

class uploadDialog(private val bitmapOfImage  :Bitmap,val yesBtn: (img:Bitmap) -> Unit) : DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            val dialogView =inflater.inflate(R.layout.dialog_upload, null)
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(dialogView)
                // Add action buttons
                .setPositiveButton("Upload",
                    DialogInterface.OnClickListener { dialog, id ->
                        // sign in the user ...
                        yesBtn(bitmapOfImage)
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()!!.cancel()
                    })
            val imageView = dialogView.findViewById<ImageView>(R.id.iv_clicked_photo)
            imageView.setImageBitmap(bitmapOfImage)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}