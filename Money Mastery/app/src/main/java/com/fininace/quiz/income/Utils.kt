package com.fininace.quiz.income

import androidx.fragment.app.Fragment
import com.fininace.quiz.income.data.Option
import com.fininace.quiz.income.data.QuizStore

fun quizStore(): QuizStoreDelegate = QuizStoreDelegate()

class QuizStoreDelegate {
    operator fun getValue(thisRef: Fragment, property: Any?): QuizStore {
        return QuizStore.instance(thisRef.requireContext())
    }
}

fun Option.clear() {
    selected = false
    showResult = false
}