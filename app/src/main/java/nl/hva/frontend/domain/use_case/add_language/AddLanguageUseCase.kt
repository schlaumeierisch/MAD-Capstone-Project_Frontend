package nl.hva.frontend.domain.use_case.add_language

import nl.hva.frontend.domain.repository.SettingsRepository
import javax.inject.Inject

class AddLanguageUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(name: String) {
        repository.addLanguage(name)
    }
}