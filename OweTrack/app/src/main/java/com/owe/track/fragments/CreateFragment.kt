package com.owe.track.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.owe.track.R
import com.owe.track.adapters.UnitAdapter
import com.owe.track.nextPaymentDate
import com.owe.track.content
import com.owe.track.data.Debt
import com.owe.track.data.IntervalUnit
import com.owe.track.databinding.FragmentCreateBinding
import com.owe.track.onEmptyFields
import com.owe.track.safeToInt
import com.owe.track.toDate
import com.owe.track.toast
import com.owe.track.viewmodels.CreateMode
import com.owe.track.viewmodels.CreateViewModel
import com.owe.track.viewmodels.DebtViewModel
import java.util.Date


class CreateFragment : Fragment(R.layout.fragment_create) {

    private val viewModel: CreateViewModel by viewModels()
    private val debtViewModel: DebtViewModel by activityViewModels()

    private lateinit var binding: FragmentCreateBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCreateBinding.bind(view)

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.save.setOnClickListener {
            save()
        }

        binding.onceCheck.setOnClickListener {
            viewModel.mode.postValue(CreateMode.Once)
        }

        binding.intervalCheck.setOnClickListener {
            viewModel.mode.postValue(CreateMode.Interval)
        }

        val adapter = UnitAdapter(requireContext(), IntervalUnit.values().toList())
        adapter.setDropDownViewResource(R.layout.item_unit_dropdown)
        binding.units.adapter = adapter

        observeStates()
    }

    private fun observeStates() {
        viewModel.mode.observe(requireActivity()) { state ->
            val isOnce = state == CreateMode.Once

            binding.date.isEnabled = isOnce
            binding.onceCheck.isChecked = isOnce

            binding.intervalCheck.isChecked = !isOnce
            binding.repeats.isEnabled = !isOnce
            binding.interval.isEnabled = !isOnce
            binding.units.isEnabled = !isOnce
        }
    }

    private fun save() {

        val title = binding.name.content
        val amount = binding.amount.content.safeToInt()
        val date = binding.date.content.toDate()
        val isOnce = binding.onceCheck.isChecked
        val isInterval = binding.intervalCheck.isChecked
        val repeats = binding.repeats.content.safeToInt()
        val interval = binding.interval.content.safeToInt()
        val unit = IntervalUnit.values()[binding.units.selectedItemPosition]
        val comment = binding.comment.content

        val fieldsIsEmpty = onEmptyFields(
            title.isBlank(),
            amount == 0,
            isOnce && date == null,
            isInterval && repeats == 0,
            isInterval && interval == 0,
        ) { toast(R.string.fields_is_empty) }

        if (fieldsIsEmpty) {
            return
        }

        val paymentDate = if (isOnce) {
            date
        } else {
            nextPaymentDate(Date(), interval, unit)
        }

        val debt = Debt(
            title = title,
            amount = amount,
            comment = comment,
            date = date,
            paymentDate = paymentDate ?: Date(),
            isOnce = isOnce,
            isInterval = isInterval,
            interval = interval,
            intervalUnit = unit,
            repeats = repeats,
        )

        debtViewModel.create(debt)

        findNavController().popBackStack()
    }
}