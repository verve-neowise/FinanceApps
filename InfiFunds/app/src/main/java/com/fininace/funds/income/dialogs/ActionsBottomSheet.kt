package com.fininace.funds.income.dialogs

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.fininace.funds.income.data.Goal
import com.fininace.funds.income.data.GoalType
import com.fininace.funds.income.viewmodels.GoalsViewModel
import com.fininace.quiz.income.R
import com.fininace.quiz.income.databinding.BottomsheetActionsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ActionsBottomSheet(
    private val goal: Goal
) : BottomSheetDialogFragment(R.layout.bottomsheet_actions) {

    private val viewModel: GoalsViewModel by activityViewModels()

    private lateinit var binding: BottomsheetActionsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = BottomsheetActionsBinding.bind(view)

        binding.archiveText.setText(
            if (goal.type == GoalType.Archived) R.string.restore else R.string.archive
        )

        binding.topUp.setOnClickListener {
            AmountDialog(AmountAction.TopUp) { amount ->

                if (goal.current == goal.total) {
                    Toast.makeText(requireContext(), R.string.message_cannot_top_up_completed, Toast.LENGTH_SHORT).show()
                    false
                }
                else if (goal.current + amount > goal.total) {
                    Toast.makeText(requireContext(), R.string.message_make_amount_smaller, Toast.LENGTH_SHORT).show()
                    false
                }
                else {
                    viewModel.topUp(goal, amount)
                    dismiss()
                    true
                }

            }.show(parentFragmentManager, "AmountDialog")
        }

        binding.withdraw.setOnClickListener {
            AmountDialog(AmountAction.Withdraw) { amount ->
                if (goal.current == 0) {
                    Toast.makeText(requireContext(), R.string.message_cannot_withdraw_from_zero_balance, Toast.LENGTH_SHORT).show()
                    false
                }
                else if (goal.current - amount < 0) {
                    Toast.makeText(requireContext(), R.string.message_cannot_withdraw_from_zero_balance, Toast.LENGTH_SHORT).show()
                    false
                }
                else {
                    viewModel.withdraw(goal, amount)
                    dismiss()
                    true
                }
            }.show(parentFragmentManager, "AmountDialog")
        }

        binding.archive.setOnClickListener {
            viewModel.archive(goal)
            dismiss()
        }

        binding.delete.setOnClickListener {
            viewModel.delete(goal)
            dismiss()
        }
    }
}