package io.monever.quotes.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Author(
    val id: Int? = null,
    val name: String = "",
    val dates: String = "",
    val avatar: String = "",
    val text: String = "",
    val link: String = "",
    val isFavorite: MutableState<Boolean> = mutableStateOf(false)
)