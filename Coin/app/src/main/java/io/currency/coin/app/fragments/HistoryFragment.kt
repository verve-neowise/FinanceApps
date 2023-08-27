package io.currency.coin.app.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import io.currency.coin.app.R
import io.currency.coin.app.adapters.HistoryAdapter
import io.currency.coin.app.databinding.FragmentHistoryBinding
import io.currency.coin.app.viewmodels.HistoryViewModel

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private val viewModel: HistoryViewModel by viewModels()

    private lateinit var binding: FragmentHistoryBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHistoryBinding.bind(view)
        binding.currencies.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onStart() {
        super.onStart()

        viewModel.history.observe(requireActivity()) { list ->
            binding.currencies.adapter = HistoryAdapter(list.toMutableList())
        }

        viewModel.fetch()
    }
}