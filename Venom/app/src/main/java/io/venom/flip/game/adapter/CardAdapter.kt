package io.venom.flip.game.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import io.venom.flip.game.Card
import io.venom.flip.game.Constants
import io.venom.flip.game.R
import io.venom.flip.game.databinding.FlipCard2Binding

class CardAdapter(
    private val cards: List<Card>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flip_card2, parent, false)
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cards[position])
        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }

    class CardViewHolder(view: View) : ViewHolder(view) {

        private val binding = FlipCard2Binding.bind(view)

        init {
            binding.root.flipDuration = Constants.FlipDuration
        }

        fun bind(card: Card) {
            val image = binding.root.findViewById<ImageView>(R.id.image)
            image.setImageResource(card.imageRes)

            if (card.show) {
                binding.root.setFlipTypeFromFront()
            }
            else {
                binding.root.setFlipTypeFromBack()
            }
            binding.root.flipTheView(true)
        }
    }
}