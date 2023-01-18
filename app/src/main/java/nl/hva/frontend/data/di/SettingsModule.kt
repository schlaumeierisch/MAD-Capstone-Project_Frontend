package nl.hva.frontend.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.hva.frontend.common.api.VocabilityAPIService
import nl.hva.frontend.data.repository.SettingsRepositoryImpl
import nl.hva.frontend.domain.repository.SettingsRepository
import nl.hva.frontend.domain.use_case.SettingsUseCases
import nl.hva.frontend.domain.use_case.add_difficulty_level.AddDifficultyLevelUseCase
import nl.hva.frontend.domain.use_case.add_flashcard.AddFlashcardUseCase
import nl.hva.frontend.domain.use_case.add_language.AddLanguageUseCase
import nl.hva.frontend.domain.use_case.delete_difficulty_level_by_id.DeleteDifficultyLevelByIdUseCase
import nl.hva.frontend.domain.use_case.delete_flashcard_by_id.DeleteFlashcardByIdUseCase
import nl.hva.frontend.domain.use_case.delete_language_by_id.DeleteLanguageByIdUseCase
import nl.hva.frontend.domain.use_case.get_all_languages.GetAllLanguagesUseCase
import nl.hva.frontend.domain.use_case.get_difficulty_levels_by_language_id.GetDifficultyLevelsByLanguageIdUseCase
import nl.hva.frontend.domain.use_case.get_flashcards_by_difficulty_level_id.GetFlashcardsByDifficultyLevelIdUseCase
import nl.hva.frontend.domain.use_case.get_language_by_id.GetLanguageByIdUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Provides
    @Singleton
    fun provideSettingsRepository(api: VocabilityAPIService): SettingsRepository {
        return SettingsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideSettingsUseCases(repository: SettingsRepository): SettingsUseCases {
        return SettingsUseCases(
            getAllLanguagesUseCase = GetAllLanguagesUseCase(repository),
            getLanguageByIdUseCase = GetLanguageByIdUseCase(repository),
            addLanguageUseCase = AddLanguageUseCase(repository),
            deleteLanguageByIdUseCase = DeleteLanguageByIdUseCase(repository),
            getDifficultyLevelsByLanguageIdUseCase = GetDifficultyLevelsByLanguageIdUseCase(repository),
            addDifficultyLevelUseCase = AddDifficultyLevelUseCase(repository),
            deleteDifficultyLevelByIdUseCase = DeleteDifficultyLevelByIdUseCase(repository),
            getFlashcardsByDifficultyLevelIdUseCase = GetFlashcardsByDifficultyLevelIdUseCase(repository),
            addFlashcardUseCase = AddFlashcardUseCase(repository),
            deleteFlashcardByIdUseCase = DeleteFlashcardByIdUseCase(repository)
        )
    }

}