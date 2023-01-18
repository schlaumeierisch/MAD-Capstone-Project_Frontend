package nl.hva.frontend.domain.model

data class HistoryEntry(
    val id: String,
    val language: String,
    val difficultyLevel: String,
    val totalAnswers: Int,
    val correctAnswers: Int,
    val date: String
)