package com.owe.track.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.owe.track.R
import com.owe.track.context
import com.owe.track.data.Payment
import com.owe.track.databinding.ItemPaymentBinding
import com.owe.track.format
import com.owe.track.moneyFormat

class PaymentsAdapter(payments: List<Payment> = listOf()) : RecyclerView.Adapter<PaymentsAdapter.PaymentViewHolder>() {

    private var items = payments.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_payment, parent, false)
        return PaymentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class PaymentViewHolder(view: View) : ViewHolder(view) {

        private val binding = ItemPaymentBinding.bind(view)

        fun bind(payment: Payment) {
            binding.title.text = payment.target
            binding.amount.text = payment.amount.moneyFormat
            binding.date.text = payment.date.format()
            binding.status.text = when {
                payment.isOnce || payment.isComplete -> context.getString(R.string.completed)
                else -> context.getString(R.string.iteration_template, payment.iteration)
            }
        }
    }
}