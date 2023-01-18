package nl.hva.frontend.domain.use_case.add_flashcard

import nl.hva.frontend.domain.repository.SettingsRepository
import javax.inject.Inject

class AddFlashcardUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(front: String, back: String, difficultyLevelId: String) {
        repository.addFlashcard(front, back, difficultyLevelId)
    }
}