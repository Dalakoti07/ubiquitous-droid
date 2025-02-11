package com.example.ubiquitousdroid.activitiesandfragments

import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_imgur.*


/**
 * A simple [Fragment] subclass.
 */
class imgurfragment : Fragment() {
    private lateinit var viewModel: getImagesViewModel
    private lateinit var adapter: imageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_imgur, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUI()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this
        ).get(getImagesViewModel::class.java)
    }

    private fun setupUI() {
        adapter = imageAdapter(arrayListOf(),{ item:ImageObject -> rvItemClicked(item) })
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getImages().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { serverResponse -> retrieveList(serverResponse.listOfImages) }
                    }
                    status.ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }
    private fun retrieveList(images: List<ImageObject>) {
        if(images.size>10){
            adapter.apply {
                addUsers(images.subList(0,16))
                notifyDataSetChanged()
            }
        }else{
            adapter.apply {
                addUsers(images)
                notifyDataSetChanged()
            }
        }
    }

    private fun rvItemClicked(objecty:ImageObject){
//        Toast.makeText(context," name : "+objecty.name,Toast.LENGTH_SHORT).show()
        val addPhotoBottomDialogFragment =
            FullBottomSheetDialogFragment()
        val bundle = Bundle()
        bundle.putString("title", objecty.name)
        bundle.putString("imageUrl",objecty.url)
        addPhotoBottomDialogFragment.arguments = bundle
//        addPhotoBottomDialogFragment.setData(objecty)
        addPhotoBottomDialogFragment.show(
            activity!!.supportFragmentManager,
            "add_photo_dialog_fragment"
        )
    }
}
