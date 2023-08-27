package io.monever.quotes.models

data class Favorite(
    val id: Int? = null,
    val type: FavoriteType
)

enum class FavoriteType {
    Author,
    Quote
}