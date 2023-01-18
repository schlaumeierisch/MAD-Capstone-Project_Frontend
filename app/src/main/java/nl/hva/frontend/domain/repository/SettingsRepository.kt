package nl.hva.frontend.domain.repository

import nl.hva.frontend.domain.model.DifficultyLevel
import nl.hva.frontend.domain.model.Flashcard
import nl.hva.frontend.domain.model.Language

interface SettingsRepository {

    suspend fun getAllLanguages(): ArrayList<Language>

    suspend fun getLanguageById(id: String): Language

    suspend fun addLanguage(name: String)

    suspend fun deleteLanguageById(id: String)

    suspend fun getDifficultyLevelsByLanguageId(id: String): ArrayList<DifficultyLevel>

    suspend fun addDifficultyLevel(name: String, languageId: String)

    suspend fun deleteDifficultyLevelById(id: String)

    suspend fun getFlashcardsByDifficultyLevelId(difficultyLevelId: String): ArrayList<Flashcard>

    suspend fun addFlashcard(front: String, back: String, difficultyLevelId: String)

    suspend fun deleteFlashcardById(id: String)

}