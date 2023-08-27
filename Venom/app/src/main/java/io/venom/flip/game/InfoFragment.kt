package io.venom.flip.game

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.venom.flip.game.databinding.FragmentInfoBinding
import io.venom.flip.game.databinding.FragmentMenuBinding

class InfoFragment : Fragment(R.layout.fragment_info) {

    private lateinit var binding: FragmentInfoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentInfoBinding.bind(view)
        binding.menu.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}