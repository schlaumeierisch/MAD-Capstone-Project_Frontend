package nl.hva.frontend.data.repository

import nl.hva.frontend.common.api.VocabilityAPIService
import nl.hva.frontend.domain.model.DifficultyLevel
import nl.hva.frontend.domain.model.Flashcard
import nl.hva.frontend.domain.model.Language
import nl.hva.frontend.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val api: VocabilityAPIService
) : SettingsRepository {

    override suspend fun getAllLanguages(): ArrayList<Language> {
        return api.getAllLanguages()
    }

    override suspend fun getLanguageById(id: String): Language {
        return api.getLanguageById(id)
    }

    override suspend fun addLanguage(name: String) {
        api.addLanguage(name)
    }

    override suspend fun deleteLanguageById(id: String) {
        api.deleteLanguageById(id)
    }

    override suspend fun getDifficultyLevelsByLanguageId(id: String): ArrayList<DifficultyLevel> {
        return api.getDifficultyLevelsByLanguageId(id)
    }

    override suspend fun addDifficultyLevel(name: String, languageId: String) {
        api.addDifficultyLevel(name, languageId)
    }

    override suspend fun deleteDifficultyLevelById(id: String) {
        api.deleteDifficultyLevelById(id)
    }

    override suspend fun getFlashcardsByDifficultyLevelId(difficultyLevelId: String): ArrayList<Flashcard> {
        return api.getFlashcardsByDifficultyLevelId(difficultyLevelId)
    }

    override suspend fun addFlashcard(front: String, back: String, difficultyLevelId: String) {
        api.addFlashcard(front, back, difficultyLevelId)
    }

    override suspend fun deleteFlashcardById(id: String) {
        api.deleteFlashcardById(id)
    }

}