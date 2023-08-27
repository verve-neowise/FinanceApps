package com.fininace.quiz.income.name

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fininace.quiz.income.R
import com.fininace.quiz.income.data.QuizStore
import com.fininace.quiz.income.databinding.FragmentNameBinding

class NameFragment : Fragment(R.layout.fragment_name) {

    private lateinit var binding: FragmentNameBinding

    private val args: NameFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentNameBinding.bind(view)

        val quizStore = QuizStore.instance(requireContext())
        val category = quizStore.categories.find { it.id == args.category }

        binding.title.text = category?.title

        binding.next.setOnClickListener {

            val name = binding.name.text.toString()

            if (name.isBlank()) {
                Toast.makeText(requireContext(), R.string.enter_name, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            findNavController().navigate(NameFragmentDirections.actionFromNameToQuiz(
                category = args.category,
                name = name
            ))
        }
    }
}