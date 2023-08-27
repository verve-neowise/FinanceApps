package com.owe.track.dialogs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.owe.track.data.Debt
import com.owe.track.viewmodels.DebtViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.owe.track.R
import com.owe.track.databinding.BottomsheetDetailsBinding
import com.owe.track.format
import com.owe.track.getInterval
import com.owe.track.isToday
import com.owe.track.leftDays
import com.owe.track.moneyFormat

class DetailsBottomSheet(
    private val debt: Debt
) : BottomSheetDialogFragment(R.layout.bottomsheet_details) {

    private val viewModel: DebtViewModel by activityViewModels()

    private lateinit var binding: BottomsheetDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = BottomsheetDetailsBinding.bind(view)

        binding.title.text = debt.title
        binding.amount.text = debt.amount.moneyFormat
        binding.date.text = debt.paymentDate.format()

        binding.times.text = if (debt.isOnce)
            getString(R.string.once)
        else
            requireContext().getInterval(debt)

        binding.date.text = when {
            debt.isCompleted -> getString(R.string.completed)
            debt.isToday -> {
                getString(R.string.today)
            }
            else -> {
                "${debt.leftDays} days left"
            }
        }

        binding.comment.text = debt.comment

        val buttonVisibility = if (debt.isCompleted) View.GONE else View.VISIBLE

        binding.closeCurrrent.visibility = buttonVisibility
        binding.complete.visibility = buttonVisibility

        binding.closeCurrrent.setOnClickListener {
            viewModel.closeCurrent(debt)
            dismiss()
        }

        binding.complete.setOnClickListener {
            viewModel.complete(debt)
            dismiss()
        }
    }
}