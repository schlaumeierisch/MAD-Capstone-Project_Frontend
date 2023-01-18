package nl.hva.frontend.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.hva.frontend.common.response.Resource
import nl.hva.frontend.domain.model.DifficultyLevel
import nl.hva.frontend.domain.model.Language
import nl.hva.frontend.domain.use_case.SettingsUseCases
import nl.hva.frontend.ui.vm.state.DifficultyLevelState
import nl.hva.frontend.ui.vm.state.FlashcardState
import nl.hva.frontend.ui.vm.state.LanguageState
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsUseCases: SettingsUseCases
) : ViewModel() {

    private val _languages = MutableStateFlow(LanguageState())
    var languages: StateFlow<LanguageState> = _languages

    private val _language = MutableStateFlow(LanguageState())
    var language: StateFlow<LanguageState> = _language
    var selectedLanguage: Language? = null

    private val _difficultyLevels = MutableStateFlow(DifficultyLevelState())
    var difficultyLevels: StateFlow<DifficultyLevelState> = _difficultyLevels
    var selectedDifficultyLevel: DifficultyLevel? = null

    private val _flashcards = MutableStateFlow(FlashcardState())
    var flashcards: StateFlow<FlashcardState> = _flashcards

    fun getAllLanguages() = viewModelScope.launch(Dispatchers.IO) {
        settingsUseCases.getAllLanguagesUseCase().collect {
            when (it) {
                is Resource.Success -> {
                    _languages.value = LanguageState(languages = it.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _languages.value = LanguageState(isLoading = true)
                }
                is Resource.Error -> {
                    _languages.value = LanguageState(error = it.message ?: "Unexpected error occurred.")
                }
            }
        }
    }

    fun getLanguageById(id: String) = viewModelScope.launch(Dispatchers.IO) {
        settingsUseCases.getLanguageByIdUseCase.invoke(id).collect {
            when (it) {
                is Resource.Success -> {
                    _language.value = LanguageState(language = it.data)
                }
                is Resource.Loading -> {
                    _language.value = LanguageState(isLoading = true)
                }
                is Resource.Error -> {
                    _language.value = LanguageState(error = it.message ?: "Unexpected error occurred.")
                }
            }
        }
    }

    fun addLanguage(name: String) = viewModelScope.launch(Dispatchers.IO) {
        settingsUseCases.addLanguageUseCase.invoke(name)
    }

    fun deleteLanguageById(id: String) = viewModelScope.launch(Dispatchers.IO) {
        settingsUseCases.deleteLanguageByIdUseCase.invoke(id)
    }

    fun getDifficultyLevelsByLanguageId(id: String) = viewModelScope.launch(Dispatchers.IO) {
        settingsUseCases.getDifficultyLevelsByLanguageIdUseCase.invoke(id).collect {
            when (it) {
                is Resource.Success -> {
                    _difficultyLevels.value = DifficultyLevelState(difficultyLevels = it.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _difficultyLevels.value = DifficultyLevelState(isLoading = true)
                }
                is Resource.Error -> {
                    _difficultyLevels.value = DifficultyLevelState(error = it.message ?: "Unexpected error occurred.")
                }
            }
        }
    }

    fun addDifficultyLevel(name: String, languageId: String) = viewModelScope.launch(Dispatchers.IO) {
        settingsUseCases.addDifficultyLevelUseCase.invoke(name, languageId)
    }

    fun deleteDifficultyLevelById(id: String) = viewModelScope.launch(Dispatchers.IO) {
        settingsUseCases.deleteDifficultyLevelByIdUseCase.invoke(id)
    }

    fun getFlashcardsByDifficultyLevelId(difficultyLevelId: String) = viewModelScope.launch(Dispatchers.IO) {
        settingsUseCases.getFlashcardsByDifficultyLevelIdUseCase.invoke(difficultyLevelId).collect {
            when (it) {
                is Resource.Success -> {
                    _flashcards.value = FlashcardState(flashcards = it.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _flashcards.value = FlashcardState(isLoading = true)
                }
                is Resource.Error -> {
                    _flashcards.value = FlashcardState(error = it.message ?: "Unexpected error occurred.")
                }
            }
        }
    }

    fun addFlashcard(front: String, back: String, difficultyLevelId: String) = viewModelScope.launch(Dispatchers.IO) {
        settingsUseCases.addFlashcardUseCase.invoke(front, back, difficultyLevelId)
    }

    fun deleteFlashcardById(id: String) = viewModelScope.launch(Dispatchers.IO) {
        settingsUseCases.deleteFlashcardByIdUseCase.invoke(id)
    }

}