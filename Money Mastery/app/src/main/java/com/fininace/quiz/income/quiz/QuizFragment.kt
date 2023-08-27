package com.fininace.quiz.income.quiz

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.fininace.quiz.income.R
import com.fininace.quiz.income.databinding.FragmentQuizBinding
import com.fininace.quiz.income.quizStore

class QuizFragment : Fragment(R.layout.fragment_quiz) {

    private lateinit var binding: FragmentQuizBinding

    private val viewModel: QuizViewModel by viewModels()
    private val args: QuizFragmentArgs by navArgs()
    private val quizStore by quizStore()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentQuizBinding.bind(view)

        val category = quizStore.categories.find { it.id == args.category }

        if (category == null) {
            Toast.makeText(requireContext(), R.string.unknown_category, Toast.LENGTH_SHORT).show()
            return
        }

        val adapter = OptionAdapter(listOf()) { option ->
            viewModel.answer(option)
        }

        binding.options.layoutManager = LinearLayoutManager(requireContext())
        binding.options.adapter = adapter

        viewModel.initialize(category)

        viewModel.state.observe(requireActivity()) {
            when(it) {
                is QuizState.Initialized -> {
                    binding.title.text = it.title
                    binding.text.text = it.quiz.text
                    adapter.setItems(it.quiz.options)
                    binding.next.setText(R.string.confirm)
                    binding.linearProgressIndicator.max = it.maxProgress
                    binding.linearProgressIndicator.progress = it.progress
                }
                is QuizState.Answered -> {
                    adapter.select(it.option)
                }
                is QuizState.Confirmed -> {
                    binding.next.setText(R.string.next)
                    adapter.notifyDataSetChanged()
                }
                is QuizState.Complete -> {
                    findNavController().navigate(QuizFragmentDirections.actionFromQuizToComplete(
                        category = it.category.id,
                        name = args.name,
                        answers = BooleanArray(it.answers.size) { index -> it.answers[index] }
                    ))
                }
                else -> { }
            }
        }

        binding.next.setOnClickListener {
            when(val value = viewModel.state.value) {
                is QuizState.Confirmed -> {
                    viewModel.next()
                }
                is QuizState.Answered -> {
                    viewModel.confirm(value.option)
                }
                is QuizState.Initialized -> {
                    Toast.makeText(requireContext(), R.string.select_variant, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "state=${value}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}