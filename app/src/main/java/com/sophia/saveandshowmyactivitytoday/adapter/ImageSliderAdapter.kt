package com.sophia.saveandshowmyactivitytoday.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sophia.saveandshowmyactivitytoday.databinding.ViewpagerItemBinding
import com.sophia.saveandshowmyactivitytoday.entity.SliderImages

class ImageSliderAdapter(private val sliderImage: List<SliderImages>) :
    ListAdapter<SliderImages, ImageSliderAdapter.ImageViewHolder>(
        object : DiffUtil.ItemCallback<SliderImages>() {
            override fun areItemsTheSame(oldItem: SliderImages, newItem: SliderImages): Boolean = false

            override fun areContentsTheSame(oldItem: SliderImages, newItem: SliderImages): Boolean = false
        }
    ) {

    inner class ImageViewHolder(private val binding: ViewpagerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindSliderImage(sliderImage: SliderImages) {
            Glide.with(itemView.context)
                .load(sliderImage.image)
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(
            ViewpagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindSliderImage(sliderImage[position])
    }
}