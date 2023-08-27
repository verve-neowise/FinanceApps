package io.venom.flip.game

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import io.venom.flip.game.adapter.CardAdapter
import io.venom.flip.game.adapter.GridSpacingItemDecoration
import io.venom.flip.game.databinding.FragmentPlayBinding

class PlayFragment : Fragment(R.layout.fragment_play) {

    private val viewModel: PlayViewModel by viewModels()

    private lateinit var binding: FragmentPlayBinding
    private lateinit var adapter: CardAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPlayBinding.bind(view)

        adapter = CardAdapter(listOf(), ::flipCard)

        binding.cards.layoutManager = GridLayoutManager(context, 3)
        binding.cards.addItemDecoration(GridSpacingItemDecoration(3, 30, false))

        binding.restart.setOnClickListener {
            viewModel.restart()
        }

        viewModel.events.observe(requireActivity()) { event ->
            when(event) {
                is Event.Initialized -> {
                    adapter = CardAdapter(event.cards, ::flipCard)
                    binding.cards.adapter = adapter
                }
                is Event.UpdateCards -> {
                    event.positions.forEach {
                        adapter.notifyItemChanged(it, Unit)
                    }
                }
                is Event.ShowWinScreen -> {
                    RestartDialog {
                        viewModel.restart()
                    }.show(parentFragmentManager, "")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.restart()
    }

    private fun flipCard(position: Int) {
        viewModel.flip(position)
    }
}