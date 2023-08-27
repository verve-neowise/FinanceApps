package io.venom.flip.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed interface Event {
    data class Initialized(val cards: List<Card>) : Event
    data class UpdateCards(val positions: List<Int>) : Event {
        constructor(position: Int) : this(listOf(position))
    }
    data object ShowWinScreen : Event
}

class PlayViewModel : ViewModel() {

    private val initialCards: List<Card> = listOf(
        Card(R.drawable.card_1),
        Card(R.drawable.card_2),
        Card(R.drawable.card_3),
        Card(R.drawable.card_4),
        Card(R.drawable.card_5),
        Card(R.drawable.card_6),
    )

    val events = MutableLiveData<Event>(Event.Initialized(listOf()))

    private var cards = listOf<Card>()

    private var selectedCard = -1
    private var locked = false

    private fun init() {
        selectedCard = -1
        locked = false
        cards = (initialCards.duplicate() + initialCards.duplicate()).shuffled()
        submitEvent(Event.Initialized(cards))
    }

    fun restart() {
        init()
        val indexes = List(cards.size) { index -> index }
        viewModelScope.launch {
            delay(3000)
            cards.forEach { it.show = false }
            submitEvent(Event.UpdateCards(indexes))
        }
    }

    fun flip(position: Int) {
        if (locked || position == selectedCard || cards[position].show) {
            return
        }

        cards[position].show = true
        submitEvent(Event.UpdateCards(position))

        if (selectedCard == -1) {
            selectedCard = position
        }
        else {
            val first = cards[selectedCard]
            val second = cards[position]

            if (first.imageRes != second.imageRes) {
                locked = true
                pauseAndClose(selectedCard, position)
            }
            else {
                checkWinCombination()
            }
            selectedCard = -1
        }
    }

    private fun checkWinCombination() {
        val allShown = cards.all { it.show }
        if (allShown) {
            viewModelScope.launch {
                delay(2000)
                submitEvent(Event.ShowWinScreen)
            }
        }
    }

    private fun pauseAndClose(selectedCard: Int, position: Int) {
        viewModelScope.launch {
            delay(2000)
            cards[selectedCard].show = false
            cards[position].show = false
            submitEvent(Event.UpdateCards(listOf(selectedCard, position)))
            delay(Constants.FlipDuration.toLong()) // wait while animation end
            locked = false
        }
    }

    private fun submitEvent(event: Event) {
        events.postValue(event)
    }

    private fun List<Card>.duplicate() : List<Card> {
        return map { it.copy() }
    }
}