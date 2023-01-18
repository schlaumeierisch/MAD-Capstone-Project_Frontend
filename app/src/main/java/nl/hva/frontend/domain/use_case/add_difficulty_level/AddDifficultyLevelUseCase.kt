package nl.hva.frontend.domain.use_case.add_difficulty_level

import nl.hva.frontend.domain.repository.SettingsRepository
import javax.inject.Inject

class AddDifficultyLevelUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(name: String, languageId: String) {
        repository.addDifficultyLevel(name, languageId)
    }
}