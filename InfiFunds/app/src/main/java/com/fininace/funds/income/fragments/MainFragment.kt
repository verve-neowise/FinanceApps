package com.fininace.funds.income.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.fininace.funds.income.adapters.PagerAdapter
import com.fininace.funds.income.data.GoalType
import com.fininace.funds.income.dialogs.ActionsBottomSheet
import com.fininace.funds.income.dialogs.CreateGoalDialog
import com.fininace.funds.income.moneyFormat
import com.fininace.funds.income.viewmodels.GoalsViewModel
import com.fininace.quiz.income.R
import com.fininace.quiz.income.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: GoalsViewModel by activityViewModels()

    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMainBinding.bind(view)

        binding.add.setOnClickListener {
            CreateGoalDialog { title, amount, date ->
                viewModel.add(title, amount, date)
            }.show(parentFragmentManager, "CreateGoalDialog")
        }

        binding.viewPager.adapter = PagerAdapter(this) { goal ->
            ActionsBottomSheet(goal)
                .show(parentFragmentManager, "ActionsBottomSheet")
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.setText(R.string.all)
                1 -> tab.setText(R.string.archived)
            }
        }.attach()
    }

    override fun onStart() {
        super.onStart()

        viewModel.goals.observe(requireActivity()) { list ->
            binding.totalSavings.text = list
                .filter { it.type == GoalType.Active }
                .sumOf { it.current }
                .moneyFormat
        }

        viewModel.fetch()
    }
}