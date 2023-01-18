package nl.hva.frontend.ui.vm.state

import nl.hva.frontend.domain.model.Flashcard

data class FlashcardState(
    val isLoading: Boolean = false,
    val flashcards: List<Flashcard> = emptyList(),
    val error: String = ""
)