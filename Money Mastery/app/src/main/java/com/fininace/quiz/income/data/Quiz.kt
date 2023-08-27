package com.fininace.quiz.income.data

import com.google.gson.annotations.SerializedName

class CategoryList : ArrayList<Category>()

data class Category(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("quizzes")
    val quizzes: List<Quiz>
)

data class Quiz(
    @SerializedName("id")
    val id: Int,
    @SerializedName("text")
    val text: String,
    @SerializedName("options")
    val options: List<Option>,
    @SerializedName("rightOption")
    val rightOption: String
)

data class Option(
    @SerializedName("id")
    val id: Int,
    @SerializedName("variant")
    val variant: String,
    @SerializedName("text")
    val text: String,

    var showResult: Boolean = false,
    var isRight: Boolean = false,
    var selected: Boolean = false
)

