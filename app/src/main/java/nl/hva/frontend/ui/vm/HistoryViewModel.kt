package nl.hva.frontend.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.hva.frontend.common.response.Resource
import nl.hva.frontend.domain.use_case.HistoryUseCases
import nl.hva.frontend.ui.vm.state.HistoryState
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyUseCases: HistoryUseCases
) : ViewModel() {

    private val _historyEntries = MutableStateFlow(HistoryState())
    var historyEntries: StateFlow<HistoryState> = _historyEntries

    var language: String = ""
    var difficultyLevel: String = ""
    var totalAnswers: Int = 0
    var correctAnswers: Int = 0
    var date: LocalDateTime = LocalDateTime.now()

    fun getAllHistoryEntries() = viewModelScope.launch(Dispatchers.IO) {
        historyUseCases.getAllHistoryEntriesUseCase.invoke().collect {
            when (it) {
                is Resource.Success -> {
                    _historyEntries.value = HistoryState(historyEntries = it.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _historyEntries.value = HistoryState(isLoading = true)
                }
                is Resource.Error -> {
                    _historyEntries.value =
                        HistoryState(error = it.message ?: "Unexpected error occurred.")
                }
            }
        }
    }

    fun addHistoryEntry(
        language: String,
        difficultyLevel: String,
        totalAnswers: Int,
        correctAnswers: Int,
        date: LocalDateTime
    ) = viewModelScope.launch(Dispatchers.IO) {
        historyUseCases.addHistoryEntryUseCase.invoke(
            language, difficultyLevel, totalAnswers, correctAnswers, date
        )
    }

    fun deleteAllHistoryEntries() = viewModelScope.launch(Dispatchers.IO) {
        historyUseCases.deleteAllHistoryEntriesUseCase.invoke()
    }

    fun deleteHistoryEntryById(id: String) = viewModelScope.launch(Dispatchers.IO) {
        historyUseCases.deleteHistoryEntryByIdUseCase.invoke(id)
    }

}