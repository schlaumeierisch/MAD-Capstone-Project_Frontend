package nl.hva.frontend.domain.use_case.add_history_entry

import nl.hva.frontend.domain.repository.HistoryRepository
import java.time.LocalDateTime
import javax.inject.Inject

class AddHistoryEntryUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    suspend operator fun invoke(
        language: String,
        difficultyLevel: String,
        totalAnswers: Int,
        correctAnswers: Int,
        date: LocalDateTime
    ) {
        repository.addHistoryEntry(language, difficultyLevel, totalAnswers, correctAnswers, date)
    }
}