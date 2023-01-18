package nl.hva.frontend.domain.use_case

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

data class SettingsUseCases(
    val getAllLanguagesUseCase: GetAllLanguagesUseCase,
    val getLanguageByIdUseCase: GetLanguageByIdUseCase,
    val addLanguageUseCase: AddLanguageUseCase,
    val deleteLanguageByIdUseCase: DeleteLanguageByIdUseCase,
    val getDifficultyLevelsByLanguageIdUseCase: GetDifficultyLevelsByLanguageIdUseCase,
    val addDifficultyLevelUseCase: AddDifficultyLevelUseCase,
    val deleteDifficultyLevelByIdUseCase: DeleteDifficultyLevelByIdUseCase,
    val getFlashcardsByDifficultyLevelIdUseCase: GetFlashcardsByDifficultyLevelIdUseCase,
    val addFlashcardUseCase: AddFlashcardUseCase,
    val deleteFlashcardByIdUseCase: DeleteFlashcardByIdUseCase
)