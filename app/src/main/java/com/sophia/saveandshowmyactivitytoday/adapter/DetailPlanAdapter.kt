package com.sophia.saveandshowmyactivitytoday.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sophia.saveandshowmyactivitytoday.databinding.ItemAddDetailedBinding
import com.sophia.saveandshowmyactivitytoday.entity.DetailPlan

class DetailPlanAdapter: ListAdapter<DetailPlan, DetailPlanAdapter.DetailPlanViewHolder>(

        object : DiffUtil.ItemCallback<DetailPlan>() {
            override fun areItemsTheSame(oldItem: DetailPlan, newItem: DetailPlan): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: DetailPlan, newItem: DetailPlan): Boolean =
                oldItem.content == newItem.content
        }

    ) {
    inner class DetailPlanViewHolder(private val binding: ItemAddDetailedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(detailPlan: DetailPlan) {
            binding.tvDetailed.text = detailPlan.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailPlanViewHolder =
        DetailPlanViewHolder(
            ItemAddDetailedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DetailPlanViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}