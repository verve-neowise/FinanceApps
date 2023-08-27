package io.currency.coin.app.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import io.currency.coin.app.R
import io.currency.coin.app.data.History
import io.currency.coin.app.databinding.ItemHistoryBinding
import io.currency.coin.app.format

class HistoryAdapter(private var items: MutableList<History> = mutableListOf())
    : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<History>) {
        this.items = items.toMutableList()
        notifyDataSetChanged()
    }

    class HistoryViewHolder(view: View) : ViewHolder(view) {

        private val binding = ItemHistoryBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(history: History) {
            binding.date.text = history.date.format()
            binding.from.text = "${String.format("%.2f", history.fromValue)}${history.fromCurrency} - ${String.format("%.2f", history.toValue)}${history.toCurrency}"
        }
    }
}