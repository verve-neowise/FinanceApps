package io.venom.flip.game

import androidx.annotation.DrawableRes

data class Card(
    @DrawableRes val imageRes: Int,
    var initialized: Boolean = false,
    var show: Boolean = false
)