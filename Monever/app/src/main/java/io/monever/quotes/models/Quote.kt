package io.monever.quotes.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Quote(
    val id: Int? = null,
    val author: String = "",
    val avatar: String = "",
    val text: String = "",
    val isFavorite: MutableState<Boolean> = mutableStateOf(false)
)