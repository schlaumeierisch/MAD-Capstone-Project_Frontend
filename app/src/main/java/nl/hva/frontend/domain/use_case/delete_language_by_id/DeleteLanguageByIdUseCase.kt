package nl.hva.frontend.domain.use_case.delete_language_by_id

import nl.hva.frontend.domain.repository.SettingsRepository
import javax.inject.Inject

class DeleteLanguageByIdUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(id: String) {
        repository.deleteLanguageById(id)
    }
}