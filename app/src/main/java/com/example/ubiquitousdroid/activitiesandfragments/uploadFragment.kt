package com.example.ubiquitousdroid.activitiesandfragments

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.ubiquitousdroid.R
import com.example.ubiquitousdroid.adapters.imageAdapter
import com.example.ubiquitousdroid.models.ImageObject
import com.example.ubiquitousdroid.network.status
import com.example.ubiquitousdroid.viewModels.getImagesViewModel
import kotlinx.android.synthetic.main.fragment_imgur.progressBar
import kotlinx.android.synthetic.main.fragment_upload.*

class uploadFragment : Fragment() {
    private lateinit var viewModel: getImagesViewModel
    private lateinit var adapter: imageAdapter
    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        floating_action_button.setOnClickListener{ getCameraAndUploadImage() }
        setupViewModel()
        setupUI()
        setupObservers()
    }

    private fun getCameraAndUploadImage() {
        if(checkCameraHardware(context!!)){
            dispatchTakePictureIntent()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // make a bottom sheet and then display stuff in there
            val imageBitmap = data!!.extras!!.get("data") as Bitmap
            val uploaddialog =uploadDialog(imageBitmap, {img :Bitmap -> uploadImage(img)} )
            uploaddialog.show(activity!!.supportFragmentManager,"dialog")
//            imageView.setImageBitmap(imageBitmap)
        }
    }

    private fun uploadImage(image: Bitmap){
        Toast.makeText(context," upload clicked ",Toast.LENGTH_SHORT).show()
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }
    private fun checkCameraHardware(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
    }
    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this
        ).get(getImagesViewModel::class.java)
    }

    private fun setupUI() {
        adapter = imageAdapter(arrayListOf(),{ item: ImageObject -> rvItemClicked(item) })
        recyclerViewUploadedImages.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getUploadedImages().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    status.SUCCESS -> {
                        recyclerViewUploadedImages.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { serverResponse -> retrieveList(serverResponse.listOfImages) }
                    }
                    status.ERROR -> {
                        recyclerViewUploadedImages.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerViewUploadedImages.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(images: List<ImageObject>) {
        adapter.apply {
            addUsers(images)
            notifyDataSetChanged()
        }
    }

    private fun rvItemClicked(objecty:ImageObject){
        return
    }
}
