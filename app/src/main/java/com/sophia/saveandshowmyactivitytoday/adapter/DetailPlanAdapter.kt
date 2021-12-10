package com.sophia.saveandshowmyactivitytoday.adapter

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.Color
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
import com.sophia.saveandshowmyactivitytoday.entity.DetailPlanCheck
import com.sophia.saveandshowmyactivitytoday.register.PreferenceManager
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModel
import org.json.JSONArray

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

        @SuppressLint("ResourceAsColor")
        fun bind(detailPlan: DetailPlan, num: Int) {
            binding.tvDetailed.text = detailPlan.content
            binding.checkbox.setButtonDrawable(R.drawable.detail_plan_check)

            viewModel.planCheckLive.observeForever {
                Log.d("a", it.toString())
                for (i in it.indices) {
                    when (detailPlan.id) {
                        it[i].checkId -> {
                            binding.checkbox.visibility = View.GONE
                            binding.afterIv.visibility = View.VISIBLE
                            binding.tvDetailed.setTextColor(R.color.gray)
                        }
                    }
                }
            }

            binding.checkbox.setOnClickListener {
                viewModel.addPlanCheck(detailPlan.content, detailPlan.id)
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