package com.sophia.saveandshowmyactivitytoday.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sophia.saveandshowmyactivitytoday.CustomDialogInterface
import com.sophia.saveandshowmyactivitytoday.databinding.TodoDialogBinding
import java.text.SimpleDateFormat
import java.util.*

class DialogTodo(dialogInterface: CustomDialogInterface) : DialogFragment() {

    private var _binding: TodoDialogBinding? = null
    private val binding get() = _binding!!

    private var customDialogInterface = dialogInterface

    private val now = System.currentTimeMillis()
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN).format(now)!!

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = TodoDialogBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//        binding.date.text = sdf
//
//        binding.saveBtn.setOnClickListener {
//            val content = binding.editTodo.text.toString()
//
//            customDialogInterface.onOkButtonClicked(content)
//            dismiss()
//        }
//    }

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

            customDialogInterface.onOkButtonClicked(content)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}