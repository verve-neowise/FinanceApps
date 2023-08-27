package com.fininace.calculate.income.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fininace.calculate.income.R
import com.fininace.calculate.income.databinding.FragmentAboutBinding

class AboutFragment : Fragment(R.layout.fragment_about) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val arrow: ImageView = view.findViewById(R.id.arrow_back)

        arrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}