package com.fininace.funds.income.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fininace.funds.income.calculateMonthsLeft
import com.fininace.funds.income.context
import com.fininace.funds.income.data.Goal
import com.fininace.funds.income.moneyFormat
import com.fininace.quiz.income.R
import com.fininace.quiz.income.databinding.ItemGoalBinding

class GoalsAdapter(val onClick: (Goal) -> Unit) : RecyclerView.Adapter<GoalsAdapter.GoalViewHolder>() {

    private var items = mutableListOf<Goal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_goal, parent, false)
        return GoalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            onClick(items[position])
        }
    }

    fun update(index: Int, goal: Goal) {
        if (index in items.indices) {
            items[index] = goal
            notifyItemChanged(index)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Goal>) {
        this.items = items.toMutableList()
        notifyDataSetChanged()
    }

    class GoalViewHolder(view: View) : ViewHolder(view) {

        private val binding = ItemGoalBinding.bind(view)

        fun bind(goal: Goal) {
            binding.current.text = goal.current.moneyFormat
            binding.max.text = goal.total.moneyFormat
            binding.title.text = goal.title
            binding.leftTime.text =
                context.getString(R.string.months_left, calculateMonthsLeft(goal.endDate))
            binding.progress.max = goal.total
            binding.progress.progress = goal.current
        }
    }
}