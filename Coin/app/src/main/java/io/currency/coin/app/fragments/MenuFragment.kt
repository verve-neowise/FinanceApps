package io.currency.coin.app.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.currency.coin.app.R
import io.currency.coin.app.databinding.FragmentMenuBinding

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private lateinit var binding: FragmentMenuBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMenuBinding.bind(view)

        binding.covert.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToConvertFragment())
        }

        binding.history.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToHistoryFragment())
        }

        binding.exchangeRates.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToExchangeRatesFragment())
        }

        binding.about.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToAboutFragment())
        }
    }
}