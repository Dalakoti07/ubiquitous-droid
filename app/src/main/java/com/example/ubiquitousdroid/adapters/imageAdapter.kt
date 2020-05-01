package com.example.ubiquitousdroid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ubiquitousdroid.R
import com.example.ubiquitousdroid.models.ImageObject
import kotlinx.android.synthetic.main.item_img_layout.view.*

class imageAdapter (private val images: ArrayList<ImageObject>) : RecyclerView.Adapter<imageAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(image: ImageObject) {
            itemView.apply {
                tv_desc.text = image.name
                Glide.with(iv_photo.context)
                    .load(image.url)
                    .into(iv_photo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_img_layout, parent, false))

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(images[position])
    }

    fun addUsers(images: List<ImageObject>) {
        this.images.apply {
            clear()
            addAll(images)
        }

    }
}