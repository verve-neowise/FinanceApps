package com.fininace.quiz.income

import android.app.Application
import com.fininace.quiz.income.data.QuizStore

class QuizApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        QuizStore.instance(this)
    }
}