package com.sophia.saveandshowmyactivitytoday.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sophia.saveandshowmyactivitytoday.BottomSheet
import com.sophia.saveandshowmyactivitytoday.CheckInterface
import com.sophia.saveandshowmyactivitytoday.R
import com.sophia.saveandshowmyactivitytoday.databinding.ListItemBinding
import com.sophia.saveandshowmyactivitytoday.entity.CheckBox
import com.sophia.saveandshowmyactivitytoday.entity.TodoEntity
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModel
import java.io.Serializable

class TodoAdapter(
    private val viewModel: TodoViewModel,
) : ListAdapter<TodoEntity, TodoAdapter.TodoViewHolder>(

    object : DiffUtil.ItemCallback<TodoEntity>() {
        override fun areItemsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean =
            oldItem.id == newItem.id && oldItem.content == newItem.content
    }

), Serializable {

    private var checkPosition = mutableListOf<CheckBox>()
    private var deleteList: ArrayList<TodoEntity> = ArrayList()

    inner class TodoViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var todoViewModel: TodoViewModel

        fun bind(todo: TodoEntity, viewModel: TodoViewModel, num: Int) {
            this.todoViewModel = viewModel
            binding.tvText.text = todo.content
            binding.tvDate.text = todo.date
            binding.checkbox.setButtonDrawable(R.drawable.check_box)

            binding.checkbox.setOnCheckedChangeListener(null)

            if (num >= checkPosition.size) {
                checkPosition.add(num, CheckBox(todo.id, false))
            }

            binding.checkbox.isChecked = checkPosition[num].checked

//            binding.checkbox.setOnCheckedChangeListener { _, check ->
//                todo.check = check
//                if (todo.check) {
////                    binding.root.visibility = View.GONE
////                    val params: RecyclerView.LayoutParams = RecyclerView.LayoutParams(
////                        ViewGroup.LayoutParams.MATCH_PARENT,
////                        ViewGroup.LayoutParams.MATCH_PARENT
////                    )
////                    params.width = 0
////                    params.height = 0
////                    itemView.requestLayout()
//                    listener.checkPosition(bindingAdapterPosition)
//                } else {
//                    itemView.visibility = View.VISIBLE
//                }
//            }
            binding.checkbox.setOnClickListener {
                checkPosition[num].checked = binding.checkbox.isChecked
                deleteList.add(todo)
                viewModel.deleteList = deleteList
                viewModel.deleteTodo(todo)

//                val intent = Intent(binding.root.context, BottomSheet::class.java)
//                intent.putExtra("deleteList",deleteList)
//                ContextCompat.startActivity(binding.root.context, intent,null)


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder =
        TodoViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(currentList[position], viewModel, position)
    }
}

