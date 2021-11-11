package com.sophia.saveandshowmyactivitytoday

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sophia.saveandshowmyactivitytoday.adapter.TodoAdapter
import com.sophia.saveandshowmyactivitytoday.databinding.LayoutBottomSheetBinding
import com.sophia.saveandshowmyactivitytoday.entity.TodoEntity
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModel
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModelFactory

class BottomSheet : BottomSheetDialogFragment() {

    private var _binding: LayoutBottomSheetBinding? = null
    val binding: LayoutBottomSheetBinding
        get() = _binding!!

    private lateinit var adapter: TodoAdapter
    private var checkList = ArrayList<TodoEntity>()

    private val viewmodel by viewModels<TodoViewModel> {
        TodoViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.visibility = View.VISIBLE
        adapter = TodoAdapter(viewmodel)
        adapter.setHasStableIds(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        binding.recyclerView.adapter = adapter

        viewmodel.readDoneData.observe(viewLifecycleOwner, {
            (binding.recyclerView.adapter as TodoAdapter).submitList(it)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}