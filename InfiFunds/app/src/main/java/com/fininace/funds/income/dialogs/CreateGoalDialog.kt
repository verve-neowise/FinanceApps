package com.fininace.funds.income.dialogs

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.fininace.funds.income.content
import com.fininace.funds.income.toDate
import com.fininace.quiz.income.R
import com.fininace.quiz.income.databinding.DialogCreateGoalBinding
import java.lang.Exception
import java.util.Date
import kotlin.math.abs

class CreateGoalDialog(
    private val onCreated: (title: String, amount: Int, date: Date) -> Unit
) : DialogFragment(R.layout.dialog_create_goal) {

    private lateinit var binding: DialogCreateGoalBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = DialogCreateGoalBinding.bind(view)

        binding.create.setOnClickListener {

            try {
                val amount = abs(binding.amount.content.toFloat().toInt())
                val date = binding.date.content.toDate()
                val title = binding.title.content

                val currentDate = Date()

                if (amount == 0) {
                    Toast.makeText(requireContext(), R.string.message_zero_amount, Toast.LENGTH_SHORT).show()
                }
                else if (title.isBlank()) {
                    Toast.makeText(requireContext(), R.string.message_wrong_title, Toast.LENGTH_SHORT).show()
                }
                else if (date == null || date.before(Date())) {
                    Toast.makeText(requireContext(), R.string.message_wrong_date, Toast.LENGTH_SHORT).show()
                }
                else {
                    onCreated(title, amount, date)
                    dismiss()
                }
            }
            catch (e: Exception) {
                Toast.makeText(requireContext(), R.string.message_wrong_inputs, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getTheme(): Int = R.style.RoundedCornersDialog
}