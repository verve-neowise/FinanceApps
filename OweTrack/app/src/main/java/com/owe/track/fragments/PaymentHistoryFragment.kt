package com.owe.track.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.owe.track.R
import com.owe.track.adapters.PaymentsAdapter
import com.owe.track.databinding.FragmentPaymentHistoryBinding
import com.owe.track.viewmodels.PaymentsHistoryViewModel

class PaymentHistoryFragment : Fragment(R.layout.fragment_payment_history) {

    private val viewModel: PaymentsHistoryViewModel by activityViewModels()
    private lateinit var binding: FragmentPaymentHistoryBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPaymentHistoryBinding.bind(view)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = PaymentsAdapter()
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetch()
    }

    override fun onResume() {
        super.onResume()

        viewModel.payments.observe(requireActivity()) { payments ->
            binding.recycler.adapter = PaymentsAdapter(payments)
        }
    }
}