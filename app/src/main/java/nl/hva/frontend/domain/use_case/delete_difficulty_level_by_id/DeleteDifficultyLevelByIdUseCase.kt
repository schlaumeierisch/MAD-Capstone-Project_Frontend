package nl.hva.frontend.domain.use_case.delete_difficulty_level_by_id

import nl.hva.frontend.domain.repository.SettingsRepository
import javax.inject.Inject

class DeleteDifficultyLevelByIdUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(id: String) {
        repository.deleteDifficultyLevelById(id)
    }
}