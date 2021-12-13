package com.sophia.saveandshowmyactivitytoday.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sophia.saveandshowmyactivitytoday.interfaced.CheckListData
import com.sophia.saveandshowmyactivitytoday.R
import com.sophia.saveandshowmyactivitytoday.databinding.ListItemBinding
import com.sophia.saveandshowmyactivitytoday.entity.CheckBox
import com.sophia.saveandshowmyactivitytoday.entity.TodoEntity
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModel

class TodoAdapter(
    private val viewModel: TodoViewModel,
    private val listener: CheckListData
) : ListAdapter<TodoEntity, TodoAdapter.TodoViewHolder>(

    object : DiffUtil.ItemCallback<TodoEntity>() {
        override fun areItemsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean =
            oldItem.id == newItem.id && oldItem.content == newItem.content
    }

) {

    private var checkPosition = mutableListOf<CheckBox>()

    inner class TodoViewHolder(
        private val binding: ListItemBinding,
        private val listener: CheckListData
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var todoViewModel: TodoViewModel
        private var list: ArrayList<TodoEntity> = arrayListOf()

        fun bind(todo: TodoEntity, viewModel: TodoViewModel, num: Int) {
            this.todoViewModel = viewModel
            binding.tvText.text = todo.content
            binding.tvDate.text = todo.date
            binding.checkbox.setButtonDrawable(R.drawable.detail_plan_check)

            if (num >= checkPosition.size) {
                checkPosition.add(num, CheckBox(todo.id, true))
            }

            binding.checkbox.isChecked = checkPosition[num].checked

            binding.checkbox.setOnClickListener {
                listener.checkList(todo.content)
                viewModel.deleteTodo(todo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder =
        TodoViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener
        )

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position), viewModel, position)
    }
}

