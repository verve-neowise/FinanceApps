package com.finance.news.app.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.finance.news.app.context
import com.finance.news.app.data.Ticket
import com.finance.news.app.formatRelativeDate
import com.finance.news.app.fromUtc
import com.fininace.news.app.R
import com.fininace.news.app.databinding.ItemCardBinding
import com.squareup.picasso.Picasso

class TicketAdapter(
    val switchFavorite: (Ticket, position: Int) -> Unit,
    val share: (Ticket) -> Unit,
    val readMore: (Ticket) -> Unit
) : RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {

    private var items = mutableListOf<Ticket>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return TicketViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(items[position])

        holder.binding.share.setOnClickListener {
            share(items[position])
        }

        holder.binding.switchFavorite.setOnClickListener {
            switchFavorite(items[position], position)
        }

        holder.binding.readMore.setOnClickListener {
            readMore(items[position])
        }
    }

    fun update(index: Int, ticket: Ticket) {
        if (index in items.indices) {
            items[index] = ticket
            notifyItemChanged(index)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Ticket>) {
        this.items = items.toMutableList()
        notifyDataSetChanged()
    }

    class TicketViewHolder(view: View) : ViewHolder(view) {

        val binding = ItemCardBinding.bind(view)

        fun bind(ticket: Ticket) {
            binding.text.text = context.getString(R.string.ellipse, ticket.description.take(150))
            binding.date.text = context.getString(R.string.relate_date, ticket.publishedUtc.fromUtc()?.formatRelativeDate())
            binding.author.text = context.getString(R.string.ellipse, ticket.author.take(16))

            binding.switchFavorite.setImageResource(
                if (ticket.isFavorite) R.drawable.favorites_fill else R.drawable.favorites_large
            )

            Picasso.with(context)
                .load(ticket.publisher.faviconUrl)
                .into(binding.authorImage)

            Picasso.with(context)
                .load(ticket.imageUrl)
                .resize(400, 200)
                .centerCrop()
                .into(binding.poster)
        }
    }
}

