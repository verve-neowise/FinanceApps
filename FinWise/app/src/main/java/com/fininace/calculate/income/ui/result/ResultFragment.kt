package com.fininace.calculate.income.ui.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fininace.calculate.income.R
import com.fininace.calculate.income.data.Type
import com.fininace.calculate.income.data.overpayment.calculateAnnuityOverpayments
import com.fininace.calculate.income.data.overpayment.calculateDifferentiatedOverpayments
import com.fininace.calculate.income.data.payment.calculateAnnuityMortgageLoan
import com.fininace.calculate.income.data.payment.calculateDifferentiatedMortgageLoan
import com.fininace.calculate.income.databinding.FragmentResultBinding
import com.fininace.calculate.income.format
import com.fininace.calculate.income.formatCurrency
import com.google.android.material.tabs.TabLayoutMediator

class ResultFragment : Fragment(R.layout.fragment_result) {

    private lateinit var binding: FragmentResultBinding

    private val args: ResultFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentResultBinding.bind(view)

        binding.type.text = getString(when(args.type) {
            Type.Annuity -> R.string.annuity
            Type.Differentiated -> R.string.differentiated_short
        })

        binding.amount.text = formatCurrency(args.principal)
        binding.duration.text = args.months.toString()
        binding.percent.text = args.interestRate.format(2)

        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.pager.apply {
            isUserInputEnabled = false
            adapter = ResultViewAdapter(this@ResultFragment, listOf(
                buildGeneralPaymentsFragment(),
                buildOverpaymentFragment()
            ))
        }

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = getString(when(position) {
                0 -> R.string.general_payments
                1 -> R.string.overpayments
                else -> R.string.unknown
            })
        }.attach()
    }

    private fun buildOverpaymentFragment(): TableFragment {
        val function = when(args.type) {
            Type.Annuity -> ::calculateAnnuityOverpayments
            Type.Differentiated -> ::calculateDifferentiatedOverpayments
        }

        val header = resources.getStringArray(R.array.overpayment_header).toList()

        val overpayments = function(args.principal.toFloat(), args.interestRate / 10, args.months)

        return TableFragment(header, overpayments)
    }

    private fun buildGeneralPaymentsFragment(): TableFragment {
        val function = when(args.type) {
            Type.Annuity -> ::calculateAnnuityMortgageLoan
            Type.Differentiated -> ::calculateDifferentiatedMortgageLoan
        }

        val headers = when(args.type) {
            Type.Annuity -> resources.getStringArray(R.array.annuity_header)
            Type.Differentiated -> resources.getStringArray(R.array.differentiated_header)
        }.toList()

        val generalsPayments = function(args.principal.toFloat(), args.interestRate / 10, args.months)

        return TableFragment(headers, generalsPayments)
    }
}