package com.sophia.saveandshowmyactivitytoday.dialog

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sophia.saveandshowmyactivitytoday.databinding.DetailedPlanDialogBinding
import com.sophia.saveandshowmyactivitytoday.interfaced.PlanDialogInterface

class DetailedPlan(private val planDialog: PlanDialogInterface) : DialogFragment() {

    private var _binding: DetailedPlanDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailedPlanDialogBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.saveBtn.setOnClickListener {
            val content = binding.editTodo.text.toString()
            planDialog.detailPlan(content)
            dismiss()
        }
        numberOfLetters()
    }

    @SuppressLint("SetTextI18n")
    private fun numberOfLetters() {
        binding.editTodo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.numberOfLetters.text = "0 / 20"
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val numberInput = binding.editTodo.text.toString()
                binding.numberOfLetters.text = numberInput.length.toString() + " / 20"
            }

            override fun afterTextChanged(p0: Editable?) {
                val numberInput = binding.editTodo.text.toString()
                binding.numberOfLetters.text = numberInput.length.toString() + " / 20"
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}