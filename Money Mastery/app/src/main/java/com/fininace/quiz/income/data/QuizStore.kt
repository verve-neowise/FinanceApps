package com.fininace.quiz.income.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStreamReader

class QuizStore private constructor(val categories: CategoryList) {

    companion object {
        private var _instance: QuizStore? = null

        fun instance(context: Context): QuizStore {
            if (_instance == null) {
                val categories = try {
                    val stream = context.assets.open("quiz.json")
                    val reader = InputStreamReader(stream)
                    Gson().fromJson(reader, CategoryList::class.java)
                }
                catch (e: IOException) {
                    Log.e("QuizStore", e.toString())
                    CategoryList()
                }

                categories.forEach { category ->
                    category.quizzes.forEach { quiz ->
                        quiz.options.forEach { option ->
                            option.isRight = quiz.rightOption == option.variant
                        }
                    }
                }

                _instance = QuizStore(categories)
            }
            return _instance!!
        }
    }
}