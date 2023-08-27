package com.fininace.quiz.income.quiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fininace.quiz.income.clear
import com.fininace.quiz.income.data.Category
import com.fininace.quiz.income.data.Option
import com.fininace.quiz.income.data.Quiz

sealed interface QuizState {
    object Nothing : QuizState
    data class Initialized(
        val title: String,
        val quiz: Quiz,
        val progress: Int,
        val maxProgress: Int
    )  : QuizState
    data class Answered(val option: Option) : QuizState
    data class Complete(val category: Category, val answers: List<Boolean>) : QuizState
    object Confirmed : QuizState
}

class QuizViewModel : ViewModel() {

    private var _category: Category? = null
    private var answers: MutableList<Boolean> = mutableListOf()
    private var quiz: Quiz? = null
    private var index: Int = 0

    val state = MutableLiveData<QuizState>(QuizState.Nothing)

    fun initialize(category: Category) {
        _category = category
        answers = MutableList(category.quizzes.size) { false }
        next()
    }

    fun next() {
        _category?.let { category ->
            if (index in category.quizzes.indices) {
                quiz = category.quizzes[index]
                quiz?.options?.forEach { it.clear() }

                state.postValue(QuizState.Initialized(
                    title = category.title,
                    quiz = quiz!!,
                    progress = index + 1,
                    maxProgress = category.quizzes.size
                ))
                index++
            }
            else {
                state.postValue(QuizState.Complete(category, answers))
            }
        }
    }

    fun confirm(option: Option) {
        quiz?.let { quiz ->
            quiz.options.forEach {
                it.showResult = it.isRight
            }
            option.showResult = true

            answers[index - 1] = option.isRight

            state.postValue(QuizState.Confirmed)
        }
    }

    fun answer(option: Option) {
        if (state.value !is QuizState.Confirmed) {
            quiz?.let { quiz ->
                quiz.options.forEach { it.clear() }
                option.selected = true
                state.postValue(QuizState.Answered(option))
            }
        }
    }
}