package nl.hva.frontend.domain.use_case.delete_flashcard_by_id

import nl.hva.frontend.domain.repository.SettingsRepository
import javax.inject.Inject

class DeleteFlashcardByIdUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(id: String) {
        repository.deleteFlashcardById(id)
    }
}