package com.owe.track.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.owe.track.R
import com.owe.track.data.Debt
import com.owe.track.context
import com.owe.track.databinding.ItemDebtBinding
import com.owe.track.format
import com.owe.track.getInterval
import com.owe.track.isToday
import com.owe.track.leftDays
import com.owe.track.moneyFormat

class DebtsAdapter(val onClick: (Debt) -> Unit) : RecyclerView.Adapter<DebtsAdapter.DebtViewHolder>() {

    private var items = mutableListOf<Debt>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebtViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_debt, parent, false)
        return DebtViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DebtViewHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            onClick(items[position])
        }
    }

    fun update(index: Int, debt: Debt) {
        if (index in items.indices) {
            items[index] = debt
            notifyItemChanged(index)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Debt>) {
        this.items = items.toMutableList()
        notifyDataSetChanged()
    }

    class DebtViewHolder(view: View) : ViewHolder(view) {

        private val binding = ItemDebtBinding.bind(view)

        fun bind(debt: Debt) {
            binding.title.text = debt.title
            binding.amount.text = debt.amount.moneyFormat
            binding.date.text = debt.paymentDate.format()

            binding.mode.text = if (debt.isOnce)
                context.getString(R.string.once)
            else
                context.getInterval(debt)

            binding.leftDays.text = when {
                debt.isCompleted -> context.getString(R.string.completed)
                debt.isToday -> {
                    context.getString(R.string.today)
                }
                else -> {
                    context.getString(R.string.left_days, debt.leftDays)
                }
            }
        }
    }
}