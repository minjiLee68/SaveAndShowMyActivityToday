package com.sophia.saveandshowmyactivitytoday.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sophia.saveandshowmyactivitytoday.databinding.ListItemBinding
import com.sophia.saveandshowmyactivitytoday.databinding.ListItemCheckBinding
import com.sophia.saveandshowmyactivitytoday.entity.Check
import com.sophia.saveandshowmyactivitytoday.entity.TodoEntity

class CheckAdapter: ListAdapter<Check, CheckAdapter.CheckViewHolder>(

    object : DiffUtil.ItemCallback<Check>() {
        override fun areItemsTheSame(oldItem: Check, newItem: Check): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Check, newItem: Check): Boolean =
            oldItem.content == newItem.content && oldItem.date == newItem.date

    }

) {

    inner class CheckViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(check: Check) {
            binding.tvText.text = check.content
            binding.tvDate.text = check.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckViewHolder =
        CheckViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CheckViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}