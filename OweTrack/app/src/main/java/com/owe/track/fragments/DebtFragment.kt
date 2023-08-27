package com.owe.track.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.owe.track.R
import com.owe.track.adapters.DebtsAdapter
import com.owe.track.viewmodels.DebtViewModel
import com.owe.track.data.Debt
import com.owe.track.databinding.FragmentDebtsBinding
import com.owe.track.isToday

enum class DebtFilter(val consumer: (Debt) -> Boolean = { true }) {
    Active({ debt -> !debt.isCompleted }),
    Today({ debt -> debt.isToday && !debt.isCompleted }),
    Completed({ debt -> debt.isCompleted })
}

class DebtFragment(private val filter: DebtFilter, onClick: (Debt) -> Unit) : Fragment(R.layout.fragment_debts) {

    private val viewModel: DebtViewModel by activityViewModels()

    private lateinit var binding: FragmentDebtsBinding

    private val adapter = DebtsAdapter(onClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDebtsBinding.bind(view)
        binding.root.layoutManager = LinearLayoutManager(requireContext())
        binding.root.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        viewModel.debts.observe(this) { list ->
            val filtered = list.filter(filter.consumer)
            adapter.setItems(filtered)
        }
    }
}