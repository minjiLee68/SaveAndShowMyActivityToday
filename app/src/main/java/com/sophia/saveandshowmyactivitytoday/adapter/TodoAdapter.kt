package com.sophia.saveandshowmyactivitytoday.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sophia.saveandshowmyactivitytoday.BottomSheet
import com.sophia.saveandshowmyactivitytoday.MainActivity
import com.sophia.saveandshowmyactivitytoday.R
import com.sophia.saveandshowmyactivitytoday.databinding.ListItemBinding
import com.sophia.saveandshowmyactivitytoday.entity.CheckBox
import com.sophia.saveandshowmyactivitytoday.entity.TodoEntity
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModel

class TodoAdapter(private val viewModel: TodoViewModel
) : ListAdapter<TodoEntity, TodoAdapter.TodoViewHolder>(

    object : DiffUtil.ItemCallback<TodoEntity>() {
        override fun areItemsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean =
            oldItem.id == newItem.id && oldItem.content == newItem.content
    }

) {

    private var checkPosition = mutableListOf<CheckBox>()
    private var checkList: MutableList<TodoEntity> = mutableListOf()

    inner class TodoViewHolder(private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var todoViewModel: TodoViewModel

        fun bind(todo: TodoEntity, viewModel: TodoViewModel, num: Int) {
            this.todoViewModel = viewModel
            binding.tvText.text = todo.content
            binding.tvDate.text = todo.date
            binding.checkbox.setButtonDrawable(R.drawable.check_box)

            binding.checkbox.setOnCheckedChangeListener(null)

//            binding.checkbox.setOnCheckedChangeListener { _, check ->
//                if (check) {
//                    val todo1 = TodoEntity(
//                        todo.content, todo.year, todo.month,
//                        todo.day, todo.date, todo.id, true
//                    )
//                    viewModel.updateTodo(todo1)
//                    binding.root.visibility = View.GONE
//                    val params: RecyclerView.LayoutParams = RecyclerView.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT
//                    )
//                    params.width = 0
//                    params.height = 0
//                    binding.root.requestLayout()
//                }
//                else {
//                    val todo2 = TodoEntity(
//                        todo.content, todo.year, todo.month,
//                        todo.day, todo.date, todo.id, false
//                    )
//                    viewModel.updateTodo(todo2)
//                }
//            }

            if (num >= checkPosition.size) {
                checkPosition.add(num, CheckBox(todo.id, false))
            }

            binding.checkbox.isChecked = checkPosition[num].checked

            binding.checkbox.setOnClickListener {
                checkPosition[num].checked = binding.checkbox.isChecked

//                if (binding.checkbox.isChecked) {
//                    val todo1 = TodoEntity(
//                        todo.content, todo.year, todo.month,
//                        todo.day, todo.date, todo.id, true
//                    )
//                    viewModel.updateTodo(todo1)
//                } else {
//                    val todo2 = TodoEntity(
//                        todo.content, todo.year, todo.month,
//                        todo.day, todo.date, todo.id, false
//                    )
//                    viewModel.updateTodo(todo2)
//                }
//                checkList.add(todo)



//                val intent = Intent(itemView.context, MainActivity::class.java)
//                intent.putExtra("checkList", todo)
//                ContextCompat.startActivity(itemView.context, intent,null)

                val bottomSheet = BottomSheet()
                val bundle = Bundle()
                bundle.putSerializable("checkList", todo)
                bottomSheet.arguments = bundle

//                viewModel.deleteTodo(todo)
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

