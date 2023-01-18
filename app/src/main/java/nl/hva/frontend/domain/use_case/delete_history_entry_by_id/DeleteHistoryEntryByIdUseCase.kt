package nl.hva.frontend.domain.use_case.delete_history_entry_by_id

import nl.hva.frontend.domain.repository.HistoryRepository
import javax.inject.Inject

class DeleteHistoryEntryByIdUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    suspend operator fun invoke(id: String) {
        repository.deleteHistoryEntryById(id)
    }
}