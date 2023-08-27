package io.venom.flip.game

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.venom.flip.game.databinding.FragmentMenuBinding

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private lateinit var binding: FragmentMenuBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMenuBinding.bind(view)

        binding.play.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToPlayFragment())
        }

        binding.info.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToInfoFragment())
        }

        binding.exit.setOnClickListener {
            requireActivity().finish()
        }
    }
}