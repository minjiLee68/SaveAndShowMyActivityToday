package com.sophia.saveandshowmyactivitytoday.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sophia.saveandshowmyactivitytoday.databinding.ItemAddDetailedBinding
import com.sophia.saveandshowmyactivitytoday.entity.DetailPlan
import com.sophia.saveandshowmyactivitytoday.register.PreferenceManager
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModel

class DetailPlanAdapter(
    private val preferenceManager: PreferenceManager,
) :
    ListAdapter<DetailPlan, DetailPlanAdapter.DetailPlanViewHolder>(

        object : DiffUtil.ItemCallback<DetailPlan>() {
            override fun areItemsTheSame(oldItem: DetailPlan, newItem: DetailPlan): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: DetailPlan, newItem: DetailPlan): Boolean =
                oldItem.content == newItem.content
        }

    ) {
    inner class DetailPlanViewHolder(private val binding: ItemAddDetailedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(detailPlan: DetailPlan, num: Int) {
            binding.tvDetailed.text = detailPlan.content

            binding.checkbox.setOnClickListener {
                if (binding.checkbox.isChecked) {
                    preferenceManager.putInteger("size", num + 1)
                }
            }
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
        holder.bind(currentList[position], position)
    }
}