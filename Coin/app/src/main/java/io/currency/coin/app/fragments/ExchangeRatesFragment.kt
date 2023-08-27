package io.currency.coin.app.fragments

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import io.currency.coin.app.R
import io.currency.coin.app.adapters.CurrencyAdapter
import io.currency.coin.app.databinding.FragmentExchangeRatesBinding
import io.currency.coin.app.onTextChanged
import io.currency.coin.app.viewmodels.CurrencyViewModel

class ExchangeRatesFragment : Fragment(R.layout.fragment_exchange_rates) {

    private lateinit var binding: FragmentExchangeRatesBinding
    private lateinit var rateAdapter: CurrencyAdapter

    private val viewModel: CurrencyViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentExchangeRatesBinding.bind(view)

        rateAdapter = CurrencyAdapter()
        binding.currencies.adapter = rateAdapter
        binding.currencies.layoutManager = LinearLayoutManager(requireContext())

        viewModel.symbols.observe(requireActivity()) { symbols ->
            binding.baseCurrencyDropdown.setAdapter(ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                symbols.map { it.toString() }
            ))
        }

        viewModel.exchangeRates.observe(requireActivity()) { rates ->
            rateAdapter.setItems(rates)
        }

        binding.baseCurrencyDropdown.onTextChanged {  newCurrency ->
            updateRates(newCurrency)
        }
        viewModel.fetchExchangeRates()
    }

    private fun updateRates(newCurrency: String) {
        val targetRate = viewModel.exchangeRates.value?.find { it.code == newCurrency } ?: return
        val rates = viewModel.exchangeRates.value ?: return

        val updatedRates = rates.map {
            val relativeToTarget = 1.0 / targetRate.rate
            val convertedRate = it.rate * relativeToTarget
            it.copy(rate = convertedRate)
        }

        rateAdapter.setItems(updatedRates)
    }
}