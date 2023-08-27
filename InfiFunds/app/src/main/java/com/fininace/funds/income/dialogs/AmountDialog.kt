package com.fininace.funds.income.dialogs

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.fininace.funds.income.content
import com.fininace.funds.income.data.Goal
import com.fininace.quiz.income.R
import com.fininace.quiz.income.databinding.DialogAmountBinding
import java.lang.Exception
import kotlin.math.abs

enum class AmountAction {
    TopUp,
    Withdraw
}

class AmountDialog(
    private val action: AmountAction,
    private val onOk: (amount: Int) -> Boolean
) : DialogFragment(R.layout.dialog_amount) {

    private lateinit var binding: DialogAmountBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = DialogAmountBinding.bind(view)

        binding.title.text = when(action) {
            AmountAction.TopUp -> getString(R.string.top_up)
            AmountAction.Withdraw -> getString(R.string.withdraw)
        }

        binding.ok.backgroundTintList =
            ContextCompat.getColorStateList(requireContext(), when(action) {
                AmountAction.TopUp -> R.color.accent
                AmountAction.Withdraw -> R.color.error
            })

        binding.ok.setOnClickListener {

            try {
                val amount = abs(binding.amount.content.toFloat().toInt())

                if (amount == 0) {
                    Toast.makeText(requireContext(), R.string.message_zero_amount, Toast.LENGTH_SHORT).show()
                }
                else {
                    if (onOk(amount)) {
                        dismiss()
                    }
                }
            }
            catch (e: Exception) {
                Log.d("AmountDialog", e.toString())
                Toast.makeText(requireContext(), R.string.message_wrong_inputs, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getTheme(): Int = R.style.RoundedCornersDialog
}