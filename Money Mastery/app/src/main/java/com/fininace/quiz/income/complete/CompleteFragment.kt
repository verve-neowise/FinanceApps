package com.fininace.quiz.income.complete

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fininace.quiz.income.R
import com.fininace.quiz.income.databinding.FragmentCompleteBinding
import com.fininace.quiz.income.quizStore

class CompleteFragment : Fragment(R.layout.fragment_complete) {

    private lateinit var binding: FragmentCompleteBinding
    private val args: CompleteFragmentArgs by navArgs()
    private val quizStore by quizStore()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCompleteBinding.bind(view)

        val category = quizStore.categories.find { it.id == args.category }

        if (category == null) {
            Toast.makeText(requireContext(), "Wrong category", Toast.LENGTH_SHORT).show()
            return
        }

        binding.title.text = category.title
        binding.name.text = getString(R.string.your_result, args.name)

        val totalCount = args.answers.size
        val rightCount = args.answers.filter { it }.size

        val percents = ((rightCount.toFloat() / totalCount.toFloat()) * 100).toInt()

        binding.progress.text = getString(R.string.progress, percents, rightCount, totalCount)

        binding.mainMenu.setOnClickListener {
            findNavController().navigate(CompleteFragmentDirections.actionFromCompleteToStart())
        }
    }
}