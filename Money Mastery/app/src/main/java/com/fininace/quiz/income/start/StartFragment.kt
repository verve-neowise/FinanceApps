package com.fininace.quiz.income.start

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fininace.quiz.income.R
import com.fininace.quiz.income.data.QuizStore
import com.fininace.quiz.income.databinding.FragmentStartBinding
import com.fininace.quiz.income.name.NameFragmentDirections
import com.fininace.quiz.income.quizStore

class StartFragment : Fragment(R.layout.fragment_start) {

    private lateinit var binding: FragmentStartBinding

    private val quizStore by quizStore()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentStartBinding.bind(view)

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = CategoryAdapter(
            categories = quizStore.categories,
            onClick = { category ->
                findNavController().navigate(StartFragmentDirections.actionStartToName(
                    category = category.id
                ))
            }
        )
    }
}