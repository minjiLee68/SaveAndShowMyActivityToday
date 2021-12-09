package com.sophia.saveandshowmyactivitytoday.adapter

import android.content.ContentValues.TAG
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sophia.saveandshowmyactivitytoday.R
import com.sophia.saveandshowmyactivitytoday.databinding.ItemAddDetailedBinding
import com.sophia.saveandshowmyactivitytoday.entity.DetailPlan
import com.sophia.saveandshowmyactivitytoday.register.PreferenceManager
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModel

class DetailPlanAdapter(
    private val preferenceManager: PreferenceManager,
    private val viewModel: TodoViewModel
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

        val list: ArrayList<Int> = arrayListOf()
        val idList: ArrayList<Int> = arrayListOf()
        var count = 0
        val id = preferenceManager.getInteger("itemId")
        val detail = preferenceManager.getInteger("detailSize")

        fun bind(detailPlan: DetailPlan, num: Int) {
            binding.tvDetailed.text = detailPlan.content
            binding.checkbox.setButtonDrawable(R.drawable.detail_plan_check)


//            if (list.size > 0) {
//                for (i in 0..detail) {
//                    when (idList[i]) {
//                        list[i] -> {
//                            binding.checkbox.isChecked = true
//                            binding.checkbox.visibility = View.GONE
//                            binding.afterIv.visibility = View.VISIBLE
//                            binding.tvDetailed.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//                        }
//                    }
//                }
//            }
//            Log.d("tag1",detailPlan.id.toString())
//            Log.d("tag2",id.toString())

            binding.checkbox.setOnClickListener {
//                for (i in 0..detail) {
//                    list.add(num)
//                    Log.d("tag", num.toString())
//                }
                viewModel.increase()
                val a = viewModel.count.value

                preferenceManager.putInteger("size", a!!)
                preferenceManager.putInteger("itemId", detailPlan.id)

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