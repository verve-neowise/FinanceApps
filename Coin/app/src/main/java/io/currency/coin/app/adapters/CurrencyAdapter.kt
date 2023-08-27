package io.currency.coin.app.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import io.currency.coin.app.R
import io.currency.coin.app.context
import io.currency.coin.app.data.Rate
import io.currency.coin.app.databinding.ItemCurrencyBinding

class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    private var items = mutableListOf<Rate>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Rate>) {
        this.items = items.toMutableList()
        notifyDataSetChanged()
    }

    class CurrencyViewHolder(view: View) : ViewHolder(view) {

        private val binding = ItemCurrencyBinding.bind(view)

        fun bind(rate: Rate) {

            if (rate.name.isEmpty()) {
                binding.name.text = rate.code
                binding.amount.text = String.format("%.2f", rate.rate)
            }
            else {
                binding.name.text = rate.name
                binding.amount.text = context.getString(
                    R.string.amount_template,
                    String.format("%.2f", rate.rate),
                    rate.code
                )
            }
        }
    }
}