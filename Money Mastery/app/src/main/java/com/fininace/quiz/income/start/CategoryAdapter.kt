package com.fininace.quiz.income.start

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fininace.quiz.income.R
import com.fininace.quiz.income.data.Category
import com.fininace.quiz.income.databinding.ItemQuizBinding

class CategoryAdapter(
    private val categories: List<Category>,
    private val onClick: (Category) -> Unit,
) : Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quiz, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = categories[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    class CategoryViewHolder(view: View): ViewHolder(view) {

        private val binding = ItemQuizBinding.bind(view)

        fun bind(category: Category) {
            binding.root.text = category.title
        }
    }
}