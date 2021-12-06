package com.sophia.saveandshowmyactivitytoday.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sophia.saveandshowmyactivitytoday.TodoDialogInterface
import com.sophia.saveandshowmyactivitytoday.databinding.TodoDialogBinding
import java.text.SimpleDateFormat
import java.util.*

class DialogTodo(private val dialogInterface: TodoDialogInterface) : DialogFragment() {

    private var _binding: TodoDialogBinding? = null
    private val binding get() = _binding!!


    private val now = System.currentTimeMillis()
    private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN).format(now)!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TodoDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.date.text = sdf

        binding.saveBtn.setOnClickListener {
            val content = binding.editTodo.text.toString()

            dialogInterface.onOkButtonClicked(content)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}