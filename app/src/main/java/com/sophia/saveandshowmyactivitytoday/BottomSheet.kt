package com.sophia.saveandshowmyactivitytoday

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sophia.saveandshowmyactivitytoday.adapter.CheckAdapter
import com.sophia.saveandshowmyactivitytoday.database.TodoDatabase
import com.sophia.saveandshowmyactivitytoday.database.getTodoDatabase
import com.sophia.saveandshowmyactivitytoday.databinding.LayoutBottomSheetBinding
import com.sophia.saveandshowmyactivitytoday.entity.Check
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModel
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModelFactory

class BottomSheet : BottomSheetDialogFragment() {

    private var _binding: LayoutBottomSheetBinding? = null
    val binding: LayoutBottomSheetBinding
        get() = _binding!!

    private lateinit var adapter: CheckAdapter
    private var checkList = ArrayList<Check>()
    private lateinit var db: TodoDatabase

    private val viewmodel by viewModels<TodoViewModel> {
        TodoViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutBottomSheetBinding.inflate(inflater, container, false)
        db = getTodoDatabase(requireContext())
//        val checkData = arguments?.getSerializable("checkList") as Check?
//        if (checkData != null) {
//            checkList.add(checkData)
//        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.visibility = View.VISIBLE
        initRecyclerview()
        setObserver()
    }

    private fun initRecyclerview() {
        binding.recyclerView.let {
            adapter = CheckAdapter()
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            it.setHasFixedSize(true)
            adapter.submitList(checkList)
        }
    }

    private fun setObserver() {
        viewmodel.checkLiveData.observe(viewLifecycleOwner, {
            (binding.recyclerView.adapter as CheckAdapter).submitList(it)
            Log.d("tag","$id")
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}