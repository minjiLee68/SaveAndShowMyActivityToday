package com.sophia.saveandshowmyactivitytoday.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sophia.saveandshowmyactivitytoday.R
import com.sophia.saveandshowmyactivitytoday.databinding.ListItemBinding
import com.sophia.saveandshowmyactivitytoday.entity.CheckBox
import com.sophia.saveandshowmyactivitytoday.entity.TodoEntity
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModel

class TodoAdapter(
    private val viewModel: TodoViewModel,
) : ListAdapter<TodoEntity, TodoAdapter.TodoViewHolder>(

    object : DiffUtil.ItemCallback<TodoEntity>() {
        override fun areItemsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean =
            oldItem.id == newItem.id && oldItem.content == newItem.content
    }

) {

    private var checkPosition = mutableListOf<CheckBox>()

    inner class TodoViewHolder(private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

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
//                if (check) {
//                    val todo = TodoEntity(todo.content, todo.year, todo.month,
//                        todo.day, todo.date, todo.id, true)
//                    viewModel.updateTodo(todo)
//                }
//                else {
//                    val todo = TodoEntity(todo.content, todo.year, todo.month,
//                        todo.day, todo.date, todo.id, false)
//                    viewModel.updateTodo(todo)
//                }
//                checkPosition[num].checked = binding.checkbox.isChecked
//            }

            binding.checkbox.setOnClickListener {
                val deleteList: ArrayList<TodoEntity> = ArrayList()
                checkPosition[num].checked = binding.checkbox.isChecked

                if (binding.checkbox.isChecked) {
                    val todo1 = TodoEntity(todo.content, todo.year, todo.month,
                        todo.day, todo.date, todo.id, true)
                    viewModel.updateTodo(todo1)
                }
                else {
                    val todo2 = TodoEntity(todo.content, todo.year, todo.month,
                        todo.day, todo.date, todo.id, false)
                    viewModel.updateTodo(todo2)
                }
                deleteList.add(todo)
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
            )
        )

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(currentList[position], viewModel, position)
    }

}

