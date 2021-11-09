package com.sophia.saveandshowmyactivitytoday.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.sophia.saveandshowmyactivitytoday.CustomDialogInterface
import com.sophia.saveandshowmyactivitytoday.databinding.TodoDialogBinding

class Dialog(context: Context, dialogInterface: CustomDialogInterface): Dialog(context) {

    private lateinit var binding: TodoDialogBinding
    private var customDialogInterface = dialogInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TodoDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.saveBtn.setOnClickListener {
            val content = binding.editTodo.text.toString()

            customDialogInterface.onOkButtonClicked(content)
            dismiss()
        }
    }
}