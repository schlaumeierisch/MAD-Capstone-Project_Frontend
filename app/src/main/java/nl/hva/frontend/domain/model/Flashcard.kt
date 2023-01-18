package nl.hva.frontend.domain.model

data class Flashcard(
    val id: String,
    val front: String,
    val back: String,
    val difficultyLevelId: String
)