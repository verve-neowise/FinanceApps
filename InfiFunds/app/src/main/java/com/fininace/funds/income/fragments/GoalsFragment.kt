package com.fininace.funds.income.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fininace.funds.income.adapters.GoalsAdapter
import com.fininace.funds.income.viewmodels.GoalsViewModel
import com.fininace.funds.income.data.Goal
import com.fininace.funds.income.data.GoalType
import com.fininace.quiz.income.R
import com.fininace.quiz.income.databinding.FragmentGoalsBinding

class GoalsFragment(private val type: GoalType, onClick: (Goal) -> Unit) : Fragment(R.layout.fragment_goals) {

    private val viewModel: GoalsViewModel by activityViewModels()

    private lateinit var binding: FragmentGoalsBinding

    private val adapter = GoalsAdapter(onClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentGoalsBinding.bind(view)
        binding.root.layoutManager = LinearLayoutManager(requireContext())
        binding.root.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        viewModel.goals.observe(this) { list ->
            adapter.setItems(list.filter { it.type == type })
        }
    }
}