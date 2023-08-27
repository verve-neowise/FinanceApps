package com.owe.track.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.owe.track.adapters.PagerAdapter
import com.owe.track.dialogs.DetailsBottomSheet
import com.owe.track.viewmodels.DebtViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.owe.track.R
import com.owe.track.databinding.FragmentMainBinding
import com.owe.track.moneyFormat

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: DebtViewModel by activityViewModels()

    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMainBinding.bind(view)

        binding.addDebt.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToCreateFragment())
        }

        binding.paymentHistory.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToPaymentHistoryFragment())
        }

        binding.viewPager.adapter = PagerAdapter(this) { goal ->
            DetailsBottomSheet(goal)
                .show(parentFragmentManager, "ActionsBottomSheet")
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.setText(R.string.today)
                1 -> tab.setText(R.string.active)
                2 -> tab.setText(R.string.completed)
            }
        }.attach()
    }

    override fun onStart() {
        super.onStart()

        viewModel.debts.observe(requireActivity()) { list ->
            val amount = list
                .filter(DebtFilter.Today.consumer)
                .sumOf { it.amount }
            binding.totalSavings.text = amount.moneyFormat
        }

        viewModel.fetch()
    }
}