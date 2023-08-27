package com.fininace.calculate.income.ui.calculate

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fininace.calculate.income.R
import com.fininace.calculate.income.data.Type
import com.fininace.calculate.income.databinding.FragmentCalculateBinding
import com.fininace.calculate.income.formatCurrency
import com.fininace.calculate.income.onTextChanged
import com.fininace.calculate.income.safeToFloat
import com.fininace.calculate.income.safeToInt
import com.fininace.calculate.income.seekbarListener

class CalculateFragment : Fragment(R.layout.fragment_calculate) {

    private lateinit var binding: FragmentCalculateBinding

    private val viewModel: CalculateViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCalculateBinding.bind(view)

        binding.about.setOnClickListener {
            findNavController().navigate(R.id.from_calculate_to_about)
        }

        binding.calculate.setOnClickListener {
            val type = when(binding.typeGroup.checkedRadioButtonId) {
                R.id.annuity -> Type.Annuity
                else -> Type.Differentiated
            }

            val principal: Int = viewModel.amount.value!!.filter { it != ' ' }.safeToInt()
            val interestRate: Float = viewModel.percent.value!!.safeToFloat()
            val months: Int = viewModel.duration.value!!.safeToInt() * 12

            val resultDirection = CalculateFragmentDirections.fromCalculateToResult(
                type = type,
                principal = principal,
                interestRate = interestRate,
                months = months
            )

            findNavController().navigate(resultDirection)
        }

        observeValues()
    }

    private fun observeValues() {

        binding.amountSeekbar.setOnSeekBarChangeListener(seekbarListener {
            viewModel.amount.postValue(formatCurrency(it))
        })

        binding.durationSeekbar.setOnSeekBarChangeListener(seekbarListener {
            viewModel.duration.postValue(it.toString())
        })

        binding.percentSeekbar.setOnSeekBarChangeListener(seekbarListener {
            viewModel.percent.postValue((it.toFloat() / 10f).toString())
        })

        binding.amount.addTextChangedListener(onTextChanged { value ->
            binding.amountSeekbar.progress = value.filter { it != ' ' }.safeToInt()
        })

        binding.years.addTextChangedListener(onTextChanged { value ->
            binding.durationSeekbar.progress = value.safeToInt()
        })

        binding.percent.addTextChangedListener(onTextChanged { value ->
            binding.percentSeekbar.progress = (value.safeToFloat() * 10f).toInt()
        })

        viewModel.amount.observe(requireActivity()) {
            binding.amount.setText(it)
        }

        viewModel.duration.observe(requireActivity()) {
            binding.years.setText(it)
        }

        viewModel.percent.observe(requireActivity()) {
            binding.percent.setText(it)
        }
    }
}