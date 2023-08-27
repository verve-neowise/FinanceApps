package com.fininace.quiz.income.quiz

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fininace.quiz.income.R
import com.fininace.quiz.income.clear
import com.fininace.quiz.income.data.Option
import com.fininace.quiz.income.databinding.ItemOptionBinding

class OptionAdapter(
    private var options: List<Option>,
    private val onClick: (Option) -> Unit,
) : Adapter<OptionAdapter.OptionViewHolder>() {

    init {
        options.forEach {
            it.clear()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_option, parent, false)
        return OptionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return options.size
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        val item = options[position]
        holder.bind(item)

        if (item.selected) {
            holder.setSelected()
        }

        if (item.showResult) {
            if (item.isRight) {
                holder.setSuccess()
            }
            else {
                holder.setWrong()
            }
        }

        if (!item.selected && !item.showResult) {
            holder.setDefault()
        }

        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(options: List<Option>) {
        this.options = options
        notifyDataSetChanged()
    }

    fun select(option: Option) {
        options.forEachIndexed { index, each ->
            each.selected = (each.variant == option.variant)
            notifyItemChanged(index)
        }
    }

    class OptionViewHolder(view: View): ViewHolder(view) {

        private val binding = ItemOptionBinding.bind(view)

        fun bind(option: Option) {
            binding.variant.text = option.variant
            binding.text.text = option.text
            setDefault()
        }

        fun setSelected() {
            update(R.drawable.quiz_state_selected, R.color.black_4)
        }

        fun setWrong() {
            update(R.drawable.quiz_state_wrong, R.color.white)
        }

        fun setSuccess() {
            update(R.drawable.quiz_state_success, R.color.white)
        }

        fun setDefault() {
            update(R.drawable.quiz_selector, R.color.white)
        }

        private fun update(@DrawableRes background: Int, @ColorRes color: Int) {
            binding.root.setBackgroundResource(background)
            binding.text.setTextColor(ContextCompat.getColor(itemView.context, color))
        }
    }
}