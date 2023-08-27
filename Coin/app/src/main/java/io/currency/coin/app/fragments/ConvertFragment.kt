package io.currency.coin.app.fragments

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import io.currency.coin.app.R
import io.currency.coin.app.content
import io.currency.coin.app.data.Symbol
import io.currency.coin.app.databinding.FragmentCovertBinding
import io.currency.coin.app.safeToDouble
import io.currency.coin.app.viewmodels.CurrencyViewModel

class ConvertFragment : Fragment(R.layout.fragment_covert) {

    private val viewModel: CurrencyViewModel by activityViewModels()

    private lateinit var binding: FragmentCovertBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCovertBinding.bind(view)

        val white = ColorDrawable(ContextCompat.getColor(requireContext(), R.color.white))

        binding.fromDropdown.setDropDownBackgroundDrawable(white)
        binding.toDropdown.setDropDownBackgroundDrawable(white)

        binding.about.setOnClickListener {
            findNavController().navigate(ConvertFragmentDirections.actionConvertFragmentToAboutFragment())
        }

        binding.convert.setOnClickListener {

            val from = binding.fromDropdown.text.toString()
            val to = binding.toDropdown.text.toString()

            if (!validCurrencies(from, to)) {
                Toast.makeText(requireContext(), "Invalid currencies", Toast.LENGTH_SHORT).show()
            }
            else {
                viewModel.setCurrencies(
                    from = from,
                    to = to
                )
                viewModel.setAmounts(
                    fromAmount = binding.fromAmount.content.safeToDouble(),
                    toAmount = binding.toAmount.content.safeToDouble(),
                )
                viewModel.convert()
            }
        }

        binding.swap.setOnClickListener {
            viewModel.setCurrencies(
                from = binding.fromDropdown.text.toString(),
                to = binding.toDropdown.text.toString()
            )
            viewModel.setAmounts(
                fromAmount = binding.fromAmount.content.safeToDouble(),
                toAmount = binding.toAmount.content.safeToDouble(),
            )
            viewModel.swap()
        }

        viewModel.symbols.observe(requireActivity()) { symbols ->
            updateDropdownValues(symbols)
        }

        viewModel.fromCurrency.observe(requireActivity()) {
            binding.fromDropdown.setText(it)
        }

        viewModel.toCurrency.observe(requireActivity()) {
            binding.toDropdown.setText(it)
        }

        viewModel.fromValue.observe(requireActivity()) {
            binding.fromAmount.setText(it.toString())
        }

        viewModel.toValue.observe(requireActivity()) {
            binding.toAmount.setText(it.toString())
        }
    }

    private fun validCurrencies(from: String, to: String): Boolean {
        val fromCurrency = viewModel.symbols.value?.find { it.code.lowercase() == from.lowercase() }
        val toCurrency = viewModel.symbols.value?.find { it.code.lowercase() == to.lowercase() }

        return fromCurrency != null && toCurrency != null
    }

    private fun updateDropdownValues(symbols: List<Symbol>) {
        binding.fromDropdown.setAdapter(ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            symbols.map { it.toString() }
        ))

        binding.toDropdown.setAdapter(ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            symbols.map { it.toString() }
        ))
    }
}